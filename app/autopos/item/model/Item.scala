package autopos.item.model

import autopos.common.service.repo.DbConfig
import autopos.item.model.Brand.brandIdReads
import autopos.item.model.Category.categoryIdReads
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads.{maxLength, minLength}
import play.api.libs.json._


case class Item(name: String,
                description: Option[String],
                remarks: Option[String],
                markedPrice: BigDecimal,
                category: Option[Category],
                brand: Option[Brand],
                code: String = "",
                id: Int = 0)

object Item {

  def create(i: ItemSchema, brand: Option[Brand], category: Option[Category]) =
    Item(i.name,
      i.description,
      i.remarks,
      i.markedPrice,
      category,
      brand,
      i.code,
      i.id)

  def fromSchemaTuple = (Item.create _).tupled


  implicit val itemReads = (
    (__ \ "name").read(minLength[String](1) andKeep maxLength[String](50)) ~
      (__ \ "description").readNullable(maxLength[String](250)) ~
      (__ \ "remarks").readNullable(maxLength[String](250)) ~
      (__ \ "markedPrice").read[BigDecimal] ~
      (__ \ "category").readNullable[Category](categoryIdReads) ~
      (__ \ "brand").readNullable[Brand](brandIdReads)
    ) (Item(_, _, _, _, _, _))

  implicit val itemWrites = Json.writes[Item]

}

case class ItemSchema(name: String,
                      description: Option[String],
                      remarks: Option[String],
                      markedPrice: BigDecimal,
                      categoryId: Option[Int],
                      brandId: Option[Int],
                      code: String = "",
                      id: Int = 0)

object ItemSchema {

  def fromItem(i: Item) =
    ItemSchema(i.name, i.description, i.remarks, i.markedPrice, i.category.map(_.id), i.brand.map(_.id), i.code, i.id)
}

trait ItemDbModule {
  self: DbConfig with BrandDbModule with CategoryDbModule with TagDbModule =>

  import driver.api.{Tag => SlickTag, _}

  protected final lazy val items = TableQuery[ItemTable]
  protected final lazy val itemTags = TableQuery[ItemTagTable]

  private[ItemDbModule] class ItemTable(tag: SlickTag) extends Table[ItemSchema](tag, "item") {


    def name = column[String]("name", O.Length(50))

    def description = column[Option[String]]("description", O.Length(250))

    def remarks = column[Option[String]]("remarks", O.Length(250))

    def markedPrice = column[BigDecimal]("marked_price")

    def categoryId = column[Option[Int]]("category_id")

    def category = foreignKey("item_category_id_fkey", categoryId, categories)(_.id)

    def brandId = column[Option[Int]]("brand_id")

    def brand = foreignKey("item_brand_id_fkey", brandId, brands)(_.id)

    def code = column[String]("code", O.Length(7), O.AutoInc) // AutoInc fields are not inserted

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def * = (name, description, remarks, markedPrice, categoryId, brandId, code, id) <>
      ((ItemSchema.apply _).tupled, ItemSchema.unapply)
  }


  /* ******************************** */

  private[ItemDbModule] class ItemTagTable(slickTag: SlickTag) extends Table[(Int, Int)](slickTag, "item_tag") {

    def itemId = column[Int]("item_id")

    def item = foreignKey("item_tag_item_id_fkey", itemId, items)(_.id)

    def tagId = column[Int]("tag_id")

    def tag = foreignKey("item_tag_tag_id_fkey", tagId, tags)(_.id)

    def * = (itemId, tagId)

  }

}

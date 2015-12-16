package autopos.item.model

import autopos.common.service.repo.HasDbConfig
import autopos.item.model.Brand.BrandTable
import autopos.item.model.Category.CategoryTable
import autopos.item.model.Tag.TagTable

case class Item(id: Long,
                name: String,
                description: Option[String],
                remarks: Option[String],
                markedPrice: BigDecimal,
                categoryId: Option[Int],
                brandId: Option[Int])


object Item extends HasDbConfig {

  import play.api.libs.json._
  import Reads.{maxLength, minLength}
  import play.api.libs.functional.syntax._

  implicit val itemReads = (
    (__ \ "name").read(minLength[String](1) andKeep maxLength[String](50)) ~
      (__ \ "description").readNullable(maxLength[String](250)) ~
      (__ \ "remarks").readNullable(maxLength[String](250)) ~
      (__ \ "markedPrice").read[BigDecimal] ~
      (__ \ "categoryId").readNullable[Int] ~
      (__ \ "brandId").readNullable[Int]
    ) (Item(0L, _, _, _, _, _, _))

  implicit val itemWrites = Json.writes[Item]


  /* ******************************** */

  import driver.api.{Tag => SlickTag, _}

  class ItemTable(tag: SlickTag) extends Table[Item](tag, "item") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name", O.Length(50))

    def description = column[Option[String]]("description", O.Length(250))

    def remarks = column[Option[String]]("remarks", O.Length(250))

    def markedPrice = column[BigDecimal]("marked_price")

    def categoryId = column[Option[Int]]("category_id")

    def category = foreignKey("item_category_id_fkey", categoryId, TableQuery[CategoryTable])(_.id)

    def brandId = column[Option[Int]]("brand_id")

    def brand = foreignKey("item_brand_id_fkey", brandId, TableQuery[BrandTable])(_.id)

    def * = (
      id,
      name,
      description,
      remarks,
      markedPrice,
      categoryId,
      brandId) <>((Item.apply _).tupled, Item.unapply)
  }


  /* ******************************** */

  class ItemTagTable(slickTag: SlickTag) extends Table[(Long, Int)](slickTag, "item_tag") {

    def itemId = column[Long]("item_id")

    def item = foreignKey("item_tag_item_id_fkey", itemId, TableQuery[ItemTable])(_.id)

    def tagId = column[Int]("tag_id")

    def tag = foreignKey("item_tag_tag_id_fkey", tagId, TableQuery[TagTable])(_.id)

    def * = (itemId, tagId)

  }


}
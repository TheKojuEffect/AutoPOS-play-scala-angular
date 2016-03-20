package autopos.item.model

import java.time.LocalDateTime
import java.time.LocalDateTime.now

import autopos.item.model.Brand.brandIdReads
import autopos.item.model.Category.categoryIdReads
import autopos.shared.model.Audited
import autopos.shared.service.repo.HasDbConfig
import autopos.shared.web.format.CommonReads.{max250String, min1AndMax50String, requiredAndGreaterThanZero}
import play.api.libs.functional.syntax._
import play.api.libs.json._


case class ItemDto(name: String,
                   description: Option[String],
                   location: Option[String],
                   remarks: Option[String],
                   markedPrice: BigDecimal,
                   category: Option[Category],
                   brand: Option[Brand],
                   code: String = "",
                   id: Long = 0,
                   createdOn: LocalDateTime = now,
                   lastUpdatedOn: LocalDateTime = now)
  extends Audited

object ItemDto {


  def create(i: Item, iq: ItemQuantity, brand: Option[Brand], category: Option[Category]) =
    ItemDto(i.name,
      i.description,
      i.location,
      i.remarks,
      i.markedPrice,
      category,
      brand,
      i.code,
      i.id)

  def fromSchemaTuple = (ItemDto.create _).tupled

  val nameReads = min1AndMax50String("name")
  val descriptionReads = max250String("description")
  val locationReads = max250String("location")
  val remarksReads = max250String("remarks")
  val markedPriceReads = requiredAndGreaterThanZero("markedPrice")

  implicit val itemReads = (
    nameReads ~
      descriptionReads ~
      locationReads ~
      remarksReads ~
      markedPriceReads ~
      (__ \ "category").readNullable[Category](categoryIdReads) ~
      (__ \ "brand").readNullable[Brand](brandIdReads)
    ) (ItemDto(_, _, _, _, _, _, _))

  implicit val itemWrites = Json.writes[ItemDto]

}

case class Item(name: String,
                description: Option[String],
                location: Option[String],
                remarks: Option[String],
                markedPrice: BigDecimal,
                categoryId: Option[Long],
                brandId: Option[Long],
                code: String = "",
                id: Long = 0,
                createdOn: LocalDateTime = now,
                lastUpdatedOn: LocalDateTime = now)
  extends Audited

object Item {

  def fromItem(i: ItemDto) =
    Item(i.name, i.description, i.location, i.remarks, i.markedPrice, i.category.map(_.id), i.brand.map(_.id), i.code, i.id)
}

trait ItemDbModule {
  self: HasDbConfig with BrandDbModule with CategoryDbModule with TagDbModule =>

  import driver.api.{Tag => SlickTag, _}

  protected final lazy val items = TableQuery[ItemTable]
  protected final lazy val itemTags = TableQuery[ItemTagTable]

  private[ItemDbModule] class ItemTable(tag: SlickTag)
    extends Table[Item](tag, "item") {


    def name = column[String]("name", O.Length(50))

    def description = column[Option[String]]("description", O.Length(250))

    def location = column[Option[String]]("location", O.Length(250))

    def remarks = column[Option[String]]("remarks", O.Length(250))

    def markedPrice = column[BigDecimal]("marked_price")

    def categoryId = column[Option[Long]]("category_id")

    def category = foreignKey("item_category_id_fkey", categoryId, categories)(_.id.?)

    def brandId = column[Option[Long]]("brand_id")

    def brand = foreignKey("item_brand_id_fkey", brandId, brands)(_.id.?)

    def code = column[String]("code", O.Length(14), O.AutoInc) // AutoInc fields are not inserted

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def createdOn = column[LocalDateTime]("created_on")

    def lastUpdatedOn = column[LocalDateTime]("last_updated_on")

    def * = (name, description, location, remarks, markedPrice, categoryId, brandId, code, id, createdOn, lastUpdatedOn) <>
      ((Item.apply _).tupled, Item.unapply)

  }


  /* ******************************** */

  private[ItemDbModule] class ItemTagTable(slickTag: SlickTag)
    extends Table[(Long, Long)](slickTag, "item_tag") {

    def itemId = column[Long]("item_id")

    def item = foreignKey("item_tag_item_id_fkey", itemId, items)(_.id)

    def tagId = column[Long]("tag_id")

    def tag = foreignKey("item_tag_tag_id_fkey", tagId, tags)(_.id)

    def * = (itemId, tagId)

  }

}

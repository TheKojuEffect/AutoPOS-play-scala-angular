package autopos.item.model

import java.time.LocalDateTime
import java.time.LocalDateTime.now

import autopos.item.service.ItemCode
import autopos.shared.format.ReadsExtensions._
import autopos.shared.model.Audited
import autopos.shared.service.repo.HasDbConfig
import play.api.libs.functional.syntax._
import play.api.libs.json._


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

  def fromItemDto(i: ItemDto) =
    Item(i.name, i.description, i.location, i.remarks, i.markedPrice, i.category.map(_.id), i.brand.map(_.id), i
      .code, i.id)
}


////////////////////////////////////////////////////////////////////////////////


case class ItemQuantity(itemId: Long,
                        quantity: Int,
                        createdOn: LocalDateTime = now,
                        lastUpdatedOn: LocalDateTime = now)
  extends Audited


////////////////////////////////////////////////////////////////////////////////

case class ItemCommand(name: String,
                       markedPrice: BigDecimal,
                       categoryId: Option[Long],
                       brandId: Option[Long],
                       quantity: Int,
                       description: Option[String],
                       location: Option[String],
                       remarks: Option[String]) {

  def toItem(id: Long = 0) =
    Item(name, description, location, remarks, markedPrice, categoryId, brandId, ItemCode(id), id)
}


object ItemCommand {

  implicit val itemCommandReads = (
    min1AndMax50String("name") ~
      requiredAndGreaterThanZero("markedPrice") ~
      optionalId("categoryId") ~
      optionalId("brandId") ~
      requiredInt("quantity") ~
      max250String("description") ~
      max250String("location") ~
      max250String("remarks")
    ) (ItemCommand.apply _)
}

////////////////////////////////////////////////////////////////////////////////

case class ItemDto(name: String,
                   description: Option[String],
                   location: Option[String],
                   remarks: Option[String],
                   markedPrice: BigDecimal,
                   quantity: Int,
                   category: Option[Category],
                   brand: Option[Brand],
                   code: String,
                   id: Long,
                   createdOn: LocalDateTime,
                   lastUpdatedOn: LocalDateTime)

object ItemDto {

  def create(i: Item, iq: ItemQuantity, brand: Option[Brand], category: Option[Category]) =
    ItemDto(i.name,
      i.description,
      i.location,
      i.remarks,
      i.markedPrice,
      iq.quantity,
      category,
      brand,
      i.code,
      i.id,
      i.createdOn,
      i.lastUpdatedOn)

  def fromTuple = (ItemDto.create _).tupled


  implicit val itemDtoWrites = Json.writes[ItemDto]

}

////////////////////////////////////////////////////////////////////////////////

trait ItemDbModule {
  self: HasDbConfig with BrandDbModule with CategoryDbModule with TagDbModule =>

  import driver.api.{Tag => SlickTag, _}

  protected final lazy val items = TableQuery[ItemTable]
  protected final lazy val itemTags = TableQuery[ItemTagTable]
  protected final lazy val itemQuantities = TableQuery[ItemQuantityTable]

  ////////////////////////////////////////////////////////////////////////////////

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

    def * = (name, description, location, remarks, markedPrice, categoryId, brandId, code, id, createdOn,
      lastUpdatedOn) <>
      ((Item.apply _).tupled, Item.unapply)

  }

  ////////////////////////////////////////////////////////////////////////////////

  private[ItemDbModule] class ItemTagTable(slickTag: SlickTag)
    extends Table[(Long, Long)](slickTag, "item_tag") {

    def itemId = column[Long]("item_id")

    def item = foreignKey("item_tag_item_id_fkey", itemId, items)(_.id)

    def tagId = column[Long]("tag_id")

    def tag = foreignKey("item_tag_tag_id_fkey", tagId, tags)(_.id)

    def * = (itemId, tagId)

  }

  ////////////////////////////////////////////////////////////////////////////////

  private[ItemDbModule] class ItemQuantityTable(tag: SlickTag)
    extends Table[ItemQuantity](tag, "item_quantity") {

    def itemId = column[Long]("item_id", O.PrimaryKey)

    def item = foreignKey("item_quantity_item_id_fkey", itemId, items)(_.id)

    def quantity = column[Int]("quantity")

    def createdOn = column[LocalDateTime]("created_on")

    def lastUpdatedOn = column[LocalDateTime]("last_updated_on")

    def * = (itemId, quantity, createdOn, lastUpdatedOn) <>
      ((ItemQuantity.apply _).tupled, ItemQuantity.unapply)

  }

}


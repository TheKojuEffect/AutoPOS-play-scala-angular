package autopos.item.model

import java.time.LocalDateTime
import java.time.LocalDateTime.now

import autopos.shared.model.Audited
import autopos.shared.service.repo.HasDbConfig

case class ItemQuantity(itemId: Long,
                        quantity: Int,
                        createdOn: LocalDateTime = now,
                        lastUpdatedOn: LocalDateTime = now)
  extends Audited


trait ItemQuantityDbModule {
  self: HasDbConfig with ItemDbModule =>

  import driver.api.{Tag => SlickTag, _}

  protected final lazy val itemQuantities = TableQuery[ItemQuantityTable]

  private[ItemQuantityDbModule] class ItemQuantityTable(tag: SlickTag)
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
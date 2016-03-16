package autopos.item.service.repo

import javax.inject.{Inject, Singleton}

import autopos.item.model._
import autopos.item.service.ItemCode
import autopos.item.structure.command.ItemCreateCommand
import autopos.shared.service.repo.{BaseRepo, BaseRepoImpl}
import autopos.shared.structure.{Page, PageImpl, Pageable}
import com.google.inject.ImplementedBy
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@ImplementedBy(classOf[ItemRepoImpl])
trait ItemRepo extends BaseRepo {

  def create(itemCreateCommand: ItemCreateCommand): Future[Long]

  def update(item: Item): Future[Int]

  def findById(id: Long): Future[Option[ItemDto]]

  def list(pageable: Pageable): Future[Page[ItemDto]]

  def delete(id: Long): Future[Int]

}


@Singleton
class ItemRepoImpl @Inject()(dbConfigProvider: DatabaseConfigProvider)
  extends BaseRepoImpl(dbConfigProvider)
    with ItemRepo
    with BrandDbModule
    with CategoryDbModule
    with TagDbModule
    with ItemQuantityDbModule
    with ItemDbModule {

  import driver.api._

  override def list(pageable: Pageable): Future[Page[ItemDto]] = {

    val itemsQuery = items
      .joinLeft(brands).on(_.brandId === _.id)
      .joinLeft(categories).on(_._1.categoryId === _.id)

    val listQuery = for {
      ((item, brand), category) <- itemsQuery
        .drop(pageable.offset)
        .take(pageable.pageSize)

    } yield (item, brand, category)

    db.run {
      for {
        elements <- listQuery.result
          .map(_.map(ItemDto.fromSchemaTuple))

        totalElements <- itemsQuery.length.result

      } yield PageImpl(elements, totalElements, pageable)
    }
  }

  override def findById(id: Long) = db.run {
    (for {
      ((item, brand), category) <- items.filter(_.id === id)
        .joinLeft(brands).on(_.brandId === _.id)
        .joinLeft(categories).on(_._1.categoryId === _.id)
    } yield (item, brand, category))
      .result.headOption
      .map(_.map(ItemDto.fromSchemaTuple(_)))
  }

  override def update(item: Item) = db.run {
    items.filter(_.id === item.id)
      .update(item.copy(code = ItemCode(item.id)))
    // Workaround to prevent item.code update
    // https://github.com/slick/slick/issues/601
  }

  override def create(itemCreateCommand: ItemCreateCommand) = {

    val item = itemCreateCommand.toItem
    val quantity = itemCreateCommand.quantity

    val createItem = (for {

      itemId <- ((items returning items.map(_.id)) += item)
      _ <- itemQuantities += ItemQuantity(itemId, quantity)

    } yield itemId).transactionally

    db run createItem
  }

  override def delete(id: Long) = db.run {
    items.filter(_.id === id)
      .delete
  }
}

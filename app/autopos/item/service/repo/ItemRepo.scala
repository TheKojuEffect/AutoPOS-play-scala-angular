package autopos.item.service.repo

import javax.inject.{Inject, Singleton}

import autopos.item.model._
import autopos.shared.service.repo.{BaseRepo, BaseRepoImpl}
import autopos.shared.structure.{Page, PageImpl, Pageable}
import com.google.inject.ImplementedBy
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@ImplementedBy(classOf[ItemRepoImpl])
trait ItemRepo extends BaseRepo {

  def create(itemCommand: ItemCommand): Future[Long]

  def update(id: Long, itemCommand: ItemCommand): Future[Int]

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
    with ItemDbModule {

  import driver.api._

  def itemsQuery = items
    .join(itemQuantities).on(_.id === _.itemId)
    .joinLeft(brands).on(_._1.brandId === _.id)
    .joinLeft(categories).on(_._1._1.categoryId === _.id)

  def itemListQuery = for {
    (((item, itemQuantity), brand), category) <- itemsQuery
  } yield (item, itemQuantity, brand, category)

  override def list(pageable: Pageable): Future[Page[ItemDto]] = db.run {
    for {
      elements <- itemListQuery
        .drop(pageable.offset)
        .take(pageable.pageSize)
        .result
        .map(_.map(i => {
          ItemDto.fromTuple(i)
        }))

      totalElements <- itemsQuery.length.result

    } yield PageImpl(elements, totalElements, pageable)
  }

  override def findById(id: Long) = db.run {
    itemListQuery.filter(_._1.id === id)
      .result.headOption
      .map(_.map(ItemDto.fromTuple(_)))
  }

  override def update(id: Long, itemCommand: ItemCommand) = db.run {
    val item = itemCommand.toItem(id)

    (for {
      _ <- itemQuantities.filter(_.itemId === id)
        .map(_.quantity)
        .update(itemCommand.quantity)

      rowsAffected <- items.filter(_.id === id)
        .update(item)
    } yield rowsAffected)
      .transactionally
  }

  override def create(itemCommand: ItemCommand) = {

    val item = itemCommand.toItem()
    val quantity = itemCommand.quantity

    val createItem = (for {

      itemId <- ((items returning items.map(_.id)) += item)
      _ <- itemQuantities += ItemQuantity(itemId, quantity)

    } yield itemId).transactionally

    db run createItem
  }

  override def delete(id: Long) = db.run {
    (for {
      _ <- itemQuantities.filter(_.itemId === id).delete
      rowsAffected <- items.filter(_.id === id).delete
    } yield rowsAffected)
      .transactionally
  }
}

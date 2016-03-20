package autopos.item.service

import javax.inject.{Inject, Singleton}

import autopos.item.model.{Item, ItemCommand, ItemDto}
import autopos.item.service.repo.ItemRepo
import autopos.shared.structure.{Page, Pageable}
import com.google.inject.ImplementedBy

import scala.concurrent.Future

@ImplementedBy(classOf[ItemServiceImpl])
trait ItemService {

  def addItem(itemCommand: ItemCommand): Future[Long]

  def updateItem(id: Long, itemCommand: ItemCommand): Future[Int]

  def getItems(pageable: Pageable): Future[Page[ItemDto]]

  def getItem(id: Long): Future[Option[ItemDto]]

  def deleteItem(id: Long): Future[Int]

}

@Singleton
class ItemServiceImpl @Inject()(itemRepo: ItemRepo)
  extends ItemService {

  override def getItems(pageable: Pageable) =
    itemRepo.list(pageable)

  override def getItem(id: Long) =
    itemRepo.findById(id)

  override def updateItem(id: Long, itemCommand: ItemCommand) =
    itemRepo.update(id, itemCommand)

  override def addItem(itemCommand: ItemCommand) = {
    itemRepo.create(itemCommand)
  }

  override def deleteItem(id: Long) =
    itemRepo.delete(id)

}

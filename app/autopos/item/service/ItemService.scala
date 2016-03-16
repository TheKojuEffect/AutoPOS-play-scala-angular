package autopos.item.service

import javax.inject.{Inject, Singleton}

import autopos.item.model.{ItemDto, Item}
import autopos.item.service.repo.ItemRepo
import autopos.shared.structure.{Page, Pageable}
import com.google.inject.ImplementedBy

import scala.concurrent.Future

@ImplementedBy(classOf[ItemServiceImpl])
trait ItemService {

  def addItem(item: ItemDto): Future[Long]

  def updateItem(item: ItemDto): Future[Int]

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

  override def updateItem(item: ItemDto) =
    itemRepo.update(Item.fromItem(item))

  override def addItem(item: ItemDto) =
    itemRepo.create(Item.fromItem(item))

  override def deleteItem(id: Long) =
    itemRepo.delete(id)

}

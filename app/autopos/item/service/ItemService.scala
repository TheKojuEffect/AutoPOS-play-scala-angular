package autopos.item.service

import javax.inject.{Inject, Singleton}

import autopos.item.model.{Item, ItemSchema}
import autopos.item.service.repo.ItemRepo
import com.google.inject.ImplementedBy

import scala.concurrent.Future

@ImplementedBy(classOf[ItemServiceImpl])
trait ItemService {

  def addItem(item: Item): Future[Int]

  def updateItem(item: Item): Future[Int]

  def getItems(): Future[Seq[Item]]

  def getItem(id: Int): Future[Option[Item]]

  def deleteItem(id: Int): Future[Int]

}

@Singleton
class ItemServiceImpl @Inject()(itemRepo: ItemRepo)
  extends ItemService {

  override def getItems() =
    itemRepo.list()

  override def getItem(id: Int) =
    itemRepo.findById(id)

  override def updateItem(item: Item) =
    itemRepo.update(ItemSchema.fromItem(item))

  override def addItem(item: Item) =
    itemRepo.create(ItemSchema.fromItem(item))

  override def deleteItem(id: Int) =
    itemRepo.delete(id)

}

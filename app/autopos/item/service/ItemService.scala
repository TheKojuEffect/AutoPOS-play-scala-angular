package autopos.item.service

import javax.inject.{Inject, Singleton}

import autopos.item.model.Item
import autopos.item.service.repo.ItemRepo
import com.google.inject.ImplementedBy

import scala.concurrent.Future

@ImplementedBy(classOf[ItemServiceImpl])
trait ItemService {

  def getItems(): Future[Seq[Item]]

}

@Singleton
class ItemServiceImpl @Inject()(itemRepo: ItemRepo)
  extends ItemService {

  override def getItems() =
    itemRepo.list()
}

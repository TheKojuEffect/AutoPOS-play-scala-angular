package autopos.item.service.repo

import javax.inject.Singleton

import autopos.common.service.repo.BaseRepo
import autopos.item.model.Item
import autopos.item.model.Item.ItemTable
import com.google.inject.ImplementedBy

import scala.concurrent.Future

@ImplementedBy(classOf[ItemRepoImpl])
trait ItemRepo extends BaseRepo {

  def list(): Future[Seq[Item]]

}


@Singleton
class ItemRepoImpl
  extends ItemRepo {

  import driver.api._

  private val items = TableQuery[ItemTable]

  override def list(): Future[Seq[Item]] = db.run {
    items.result
  }
}

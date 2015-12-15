package autopos.item.service.repo

import javax.inject.Singleton

import autopos.common.service.repo.BaseRepo
import autopos.item.model.Item
import autopos.item.model.Item.{ItemTable, ItemTagTable}
import autopos.item.model.Tag.TagTable
import com.google.inject.ImplementedBy

import scala.concurrent.Future

@ImplementedBy(classOf[ItemRepoImpl])
trait ItemRepo extends BaseRepo {

  def findById(id: Long): Future[Option[Item]]

  def list(): Future[Seq[Item]]

}


@Singleton
class ItemRepoImpl
  extends ItemRepo {

  import driver.api._

  private val items = TableQuery[ItemTable]

  private val itemTags = TableQuery[ItemTagTable]

  private val tags = TableQuery[TagTable]

  override def list(): Future[Seq[Item]] = db.run {
    (for {
      (i1, it1) <- items joinLeft itemTags on (_.id === _.itemId)
    } yield i1).result
  }

  override def findById(id: Long) = db.run {
    (for {
      (i1, it1) <- items filter (_.id === id) joinLeft itemTags on (_.id === _.itemId)
    } yield i1).result.headOption
  }
}

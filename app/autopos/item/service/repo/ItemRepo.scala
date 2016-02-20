package autopos.item.service.repo

import javax.inject.Singleton
import autopos.item.model._
import autopos.item.service.ItemCode
import autopos.shared.service.repo.BaseRepo
import autopos.shared.structure.Pageable
import com.google.inject.ImplementedBy

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@ImplementedBy(classOf[ItemRepoImpl])
trait ItemRepo extends BaseRepo {

  def create(item: ItemSchema): Future[Int]

  def update(item: ItemSchema): Future[Int]

  def findById(id: Int): Future[Option[Item]]

  def list(pageable: Pageable): Future[Seq[Item]]

  def delete(id: Int): Future[Int]

}


@Singleton
class ItemRepoImpl
  extends ItemRepo with BrandDbModule with CategoryDbModule with TagDbModule with ItemDbModule {

  import driver.api._

  override def list(pageable: Pageable): Future[Seq[Item]] = db.run {
    (for {
      ((item, brand), category) <- items
        .joinLeft(brands).on(_.brandId === _.id)
        .joinLeft(categories).on(_._1.categoryId === _.id)
    } yield (item, brand, category))
      .drop(pageable.offset)
      .take(pageable.pageSize)
      .result
      .map(_.map(Item.fromSchemaTuple(_)))
  }

  override def findById(id: Int) = db.run {
    (for {
      ((item, brand), category) <- items.filter(_.id === id)
        .joinLeft(brands).on(_.brandId === _.id)
        .joinLeft(categories).on(_._1.categoryId === _.id)
    } yield (item, brand, category))
      .result.headOption
      .map(_.map(Item.fromSchemaTuple(_)))
  }

  override def update(item: ItemSchema) = db.run {
    items.filter(_.id === item.id)
      .update(item.copy(code = ItemCode(item.id)))
    // Workaround to prevent item.code update
    // https://github.com/slick/slick/issues/601
  }

  override def create(item: ItemSchema) = db.run {
    (items returning items.map(_.id)) += item
  }

  override def delete(id: Int) = db.run {
    items.filter(_.id === id)
      .delete
  }
}

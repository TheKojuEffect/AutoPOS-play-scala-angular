package autopos.item.service.repo

import javax.inject.{Inject, Singleton}

import autopos.item.model.{Category, CategoryDbModule}
import autopos.shared.service.repo.{BaseRepoImpl, BaseRepo}
import com.google.inject.ImplementedBy
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.Future


@ImplementedBy(classOf[CategoryRepoImpl])
trait CategoryRepo extends BaseRepo {

  def list(): Future[Seq[Category]]

  def create(category: Category): Future[Category]

  def update(category: Category): Future[Int]

  def findById(id: Long): Future[Option[Category]]

  def delete(id: Long): Future[Int]

}


@Singleton
class CategoryRepoImpl @Inject()(dbConfigProvider: DatabaseConfigProvider)
  extends BaseRepoImpl(dbConfigProvider)
    with CategoryRepo with CategoryDbModule {

  import driver.api._

  override def list(): Future[Seq[Category]] = db.run {
    categories.result
  }

  override def create(category: Category) = db.run {
    (categories returning (categories map (_.id))
      into ((category, id) => category.copy(id = id))
      ) += category
  }

  override def update(category: Category) = db.run {
    categories.filter(_.id === category.id)
      .update(category)
  }

  override def findById(id: Long) = db.run {
    categories.filter(_.id === id)
      .result.headOption
  }

  override def delete(id: Long) = db.run {
    categories.filter(_.id === id)
      .delete
  }
}




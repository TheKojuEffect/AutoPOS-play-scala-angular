package autopos.item.service.repo

import javax.inject.{Inject, Singleton}

import autopos.item.model.{Brand, BrandDbModule}
import autopos.shared.service.repo.{BaseRepoImpl, HasDbConfig}
import com.google.inject.ImplementedBy
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.Future


@ImplementedBy(classOf[BrandRepoImpl])
trait BrandRepo extends HasDbConfig {

  def findById(id: Long): Future[Option[Brand]]

  def list(): Future[Seq[Brand]]

  def create(brand: Brand): Future[Brand]

  def update(brand: Brand): Future[Int]

  def delete(id: Long): Future[Int]
}


@Singleton
class BrandRepoImpl @Inject()(dbConfigProvider: DatabaseConfigProvider)
  extends BaseRepoImpl(dbConfigProvider)
    with BrandRepo with BrandDbModule {

  import driver.api._

  override def findById(id: Long) = db.run {
    brands.filter(_.id === id).result.headOption
  }

  override def list(): Future[Seq[Brand]] = db.run {
    brands.result
  }

  override def create(brand: Brand) = db.run {
    (brands returning (brands.map(_.id))
      into ((brand, id) => brand.copy(id = id))
      ) += brand

  }

  override def update(brand: Brand) = db.run {
    brands.filter(_.id === brand.id)
      .update(brand)
  }

  override def delete(id: Long) = db.run {
    brands.filter(_.id === id)
      .delete
  }

}

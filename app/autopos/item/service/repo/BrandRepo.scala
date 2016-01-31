package autopos.item.service.repo

import javax.inject.Singleton

import autopos.common.service.repo.DbConfig
import autopos.item.model.{Brand, BrandDbModule}
import com.google.inject.ImplementedBy

import scala.concurrent.Future


@ImplementedBy(classOf[BrandRepoImpl])
trait BrandRepo extends DbConfig {

  def findById(id: Int): Future[Option[Brand]]

  def list(): Future[Seq[Brand]]

  def create(brand: Brand): Future[Brand]

  def update(brand: Brand): Future[Int]

  def delete(id: Int): Future[Int]
}


@Singleton
class BrandRepoImpl
  extends BrandRepo with BrandDbModule {

  import driver.api._

  override def findById(id: Int) = db.run {
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

  override def delete(id: Int) = db.run {
    brands.filter(_.id === id)
      .delete
  }

}

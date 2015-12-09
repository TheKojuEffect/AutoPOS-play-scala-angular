package autopos.item.service.repo

import javax.inject.{Inject, Singleton}

import autopos.item.model.Brand
import com.google.inject.ImplementedBy
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}


@ImplementedBy(classOf[BrandRepoImpl])
trait BrandRepo {

  def findById(id: Int): Future[Option[Brand]]

  def list(): Future[Seq[Brand]]

  def create(brand: Brand): Future[Brand]

  def update(brand: Brand): Future[Int]

  def delete(id: Int): Future[Int]
}


@Singleton
class BrandRepoImpl @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ed: ExecutionContext) extends BrandRepo {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import driver.api._

  private val brands = TableQuery[BrandTable]

  /* ********************************* */

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

  /* ********************************* */

  private class BrandTable(tag: Tag) extends Table[Brand](tag, "Brand") {

    def id = column[Option[Int]]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name", O.Length(50))

    def * = (id, name) <>((Brand.apply _).tupled, Brand.unapply)

  }

}

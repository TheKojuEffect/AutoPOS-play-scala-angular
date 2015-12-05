package autopos.item.service

import javax.inject.{Inject, Singleton}

import autopos.item.model.Brand
import com.google.inject.ImplementedBy
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}


@ImplementedBy(classOf[BrandRepoImpl])
trait BrandRepo {

  def create(name: String): Future[Brand]

  def list(): Future[Seq[Brand]]

}


@Singleton
class BrandRepoImpl @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ed: ExecutionContext) extends BrandRepo {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import driver.api._
  private val brands = TableQuery[BrandTable]

  override def create(name: String): Future[Brand] = db.run {
    (brands.map(b => (b.name))
      returning brands.map(_.id)
      into ((name, id) => Brand(id, name))
      ) += (name)
  }

  override def list(): Future[Seq[Brand]] = db.run {
    brands.result
  }

  private class BrandTable(tag: Tag) extends Table[Brand](tag, "Brand") {

    def * = (id, name) <>((Brand.apply _).tupled, Brand.unapply)

    def id = column[Option[Int]]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")
  }

}

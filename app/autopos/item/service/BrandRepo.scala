package autopos.item.service

import javax.inject.{Inject, Singleton}
import autopos.item.model.Brand
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BrandRepo @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ed: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import driver.api._

  private class BrandTable(tag: Tag) extends Table[Brand](tag, "Brand") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def * = (id, name) <>((Brand.apply _).tupled, Brand.unapply)
  }

  private val brands = TableQuery[BrandTable]

  def create(name: String): Future[Brand] = db.run {
    (brands.map(b => (b.name))
      returning brands.map(_.id)
      into ((name, id) => Brand(id, name))
      ) += (name)
  }

  def list(): Future[Seq[Brand]] = db.run {
    brands.result
  }

}

package autopos.item.service

import javax.inject.{Inject, Singleton}

import autopos.item.model.Category
import com.google.inject.ImplementedBy
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}


@ImplementedBy(classOf[CategoryRepoImpl])
trait CategoryRepo {

  def list(): Future[Seq[Category]]

}


@Singleton
class CategoryRepoImpl @Inject()(dbConfigProvider: DatabaseConfigProvider)
                                (implicit ed: ExecutionContext)
  extends CategoryRepo {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import driver.api._

  private val categories = TableQuery[CategoryTable]

  override def list(): Future[Seq[Category]] = db.run {
    categories.result
  }

  private class CategoryTable(tag: Tag) extends Table[Category](tag, "Category") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def shortName = column[String]("shortName", O.Length(3))

    def name = column[String]("name", O.Length(50))

    def * = (id, shortName, name) <>((Category.apply _).tupled, Category.unapply)

  }

}


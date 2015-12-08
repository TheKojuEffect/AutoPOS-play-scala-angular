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

  def create(category: Category): Future[Category]

  def update(category: Category): Future[Int]

  def findById(id: Int): Future[Option[Category]]

  def delete(id: Int): Future[Int]

}


@Singleton
class CategoryRepoImpl @Inject()(dbConfigProvider: DatabaseConfigProvider)
                                (implicit ed: ExecutionContext)
  extends CategoryRepo {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import driver.api._

  /* ****************************** */

  private class CategoryTable(tag: Tag) extends Table[Category](tag, "Category") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def shortName = column[String]("shortName", O.Length(3))

    def name = column[String]("name", O.Length(50))

    def * = (id, shortName, name) <>((Category.apply _).tupled, Category.unapply)

  }

  /* ****************************** */

  private val categories = TableQuery[CategoryTable]

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

  override def findById(id: Int) = db.run {
    categories.filter(_.id === id)
      .result.headOption
  }

  override def delete(id: Int) = db.run {
    categories.filter(_.id === id)
      .delete
  }

}


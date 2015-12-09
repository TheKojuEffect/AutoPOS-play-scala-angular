package autopos.item.service.repo

import javax.inject.{Inject, Singleton}

import autopos.item.model.{Tag => ItemTag}
import com.google.inject.ImplementedBy
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}


@ImplementedBy(classOf[TagRepoImpl])
trait TagRepo {

  def list(): Future[Seq[ItemTag]]

  def create(tag: ItemTag): Future[ItemTag]

  def update(tag: ItemTag): Future[Int]

  def findById(id: Int): Future[Option[ItemTag]]

  def delete(id: Int): Future[Int]
}


@Singleton
class TagRepoImpl @Inject()(dbConfigProvider: DatabaseConfigProvider)
                           (implicit ed: ExecutionContext)
  extends TagRepo {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import driver.api._

  private val tags = TableQuery[TagTable]

  /* ***************************** */

  override def create(tag: ItemTag) = db.run {
    (tags returning tags.map(_.id)
      into ((tag, id) => tag.copy(id = id))
      ) += tag
  }

  override def list(): Future[Seq[ItemTag]] = db.run {
    tags.result
  }

  override def update(tag: ItemTag) = db.run {
    tags.filter(_.id === tag.id)
      .update(tag)
  }

  override def findById(id: Int) = db.run {
    tags.filter(_.id === id).result.headOption
  }

  override def delete(id: Int) = db.run {
    tags.filter(_.id === id).delete
  }


  /* ***************************** */

  import driver.api.{Tag => SlickTag}

  private class TagTable(tag: SlickTag) extends Table[ItemTag](tag, "Tag") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name", O.Length(50))

    def * = (id, name) <>((ItemTag.apply _).tupled, ItemTag.unapply)

  }

}

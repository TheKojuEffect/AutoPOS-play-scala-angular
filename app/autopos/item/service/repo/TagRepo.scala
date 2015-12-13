package autopos.item.service.repo

import javax.inject.Singleton

import autopos.common.service.repo.HasDbConfig
import autopos.item.model.{Tag => ItemTag}
import com.google.inject.ImplementedBy

import scala.concurrent.Future


@ImplementedBy(classOf[TagRepoImpl])
trait TagRepo extends HasDbConfig {

  def list(): Future[Seq[ItemTag]]

  def create(tag: ItemTag): Future[ItemTag]

  def update(tag: ItemTag): Future[Int]

  def findById(id: Int): Future[Option[ItemTag]]

  def delete(id: Int): Future[Int]
}


@Singleton
class TagRepoImpl
  extends TagRepo {

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

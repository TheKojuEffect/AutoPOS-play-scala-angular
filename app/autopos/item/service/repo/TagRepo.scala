package autopos.item.service.repo

import javax.inject.{Inject, Singleton}

import autopos.item.model.{Tag => ItemTag, TagDbModule}
import autopos.shared.service.repo.{BaseRepoImpl, HasDbConfig}
import com.google.inject.ImplementedBy
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.Future


@ImplementedBy(classOf[TagRepoImpl])
trait TagRepo extends HasDbConfig {

  def list(): Future[Seq[ItemTag]]

  def create(tag: ItemTag): Future[ItemTag]

  def update(tag: ItemTag): Future[Int]

  def findById(id: Long): Future[Option[ItemTag]]

  def delete(id: Long): Future[Int]
}


@Singleton
class TagRepoImpl @Inject()(dbConfigProvider: DatabaseConfigProvider)
  extends BaseRepoImpl(dbConfigProvider)
    with TagRepo with TagDbModule {

  import driver.api._

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

  override def findById(id: Long) = db.run {
    tags.filter(_.id === id).result.headOption
  }

  override def delete(id: Long) = db.run {
    tags.filter(_.id === id).delete
  }

}

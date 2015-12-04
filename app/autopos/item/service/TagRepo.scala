package autopos.item.service

import javax.inject.{Inject, Singleton}

import autopos.item.model.Tag
import com.google.inject.ImplementedBy
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}


@ImplementedBy(classOf[TagRepoImpl])
trait TagRepo {

  def list(): Future[Seq[Tag]]

}

@Singleton
class TagRepoImpl @Inject()(dbConfigProvider: DatabaseConfigProvider)
                           (implicit ed: ExecutionContext)
  extends TagRepo {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import driver.api.{Tag => _, _}

  private val tags = TableQuery[TagTable]

  override def list(): Future[Seq[Tag]] = db.run {
    tags.result
  }


  import slick.lifted.{Tag => SlickTag}

  private class TagTable(tag: SlickTag) extends Table[Tag](tag, "Tag") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def * = (id, name) <>((Tag.apply _).tupled, Tag.unapply)

  }

}

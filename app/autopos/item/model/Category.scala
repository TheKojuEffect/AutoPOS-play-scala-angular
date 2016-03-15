package autopos.item.model

import java.time.LocalDateTime
import java.time.LocalDateTime.now

import autopos.shared.model.Audited
import autopos.shared.service.repo.HasDbConfig
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads.{maxLength, minLength}
import play.api.libs.json._

case class Category(shortName: String,
                    name: String,
                    id: Long = 0,
                    createdOn: LocalDateTime = now,
                    lastUpdatedOn: LocalDateTime = now)
  extends Audited


object Category {

  implicit val categoryReads = (
    (__ \ "shortName").read(minLength[String](1) andKeep maxLength[String](3)) ~
      (__ \ "name").read(minLength[String](1) andKeep maxLength[String](50))
    ) (Category.apply(_, _))

  val categoryIdReads = (__ \ "id").read[Long]
    .map(Category("", "", _))

  implicit val categoryWrites = Json.writes[Category]

}

trait CategoryDbModule {
  self: HasDbConfig =>

  import driver.api.{Tag => SlickTag, _}

  protected final lazy val categories = TableQuery[CategoryTable]

  private[CategoryDbModule] class CategoryTable(tag: SlickTag) extends Table[Category](tag, "category") {

    def shortName = column[String]("short_name", O.Length(3))

    def name = column[String]("name", O.Length(50))

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def createdOn = column[LocalDateTime]("created_on")

    def lastUpdatedOn = column[LocalDateTime]("last_updated_on")

    def * = (shortName, name, id, createdOn, lastUpdatedOn) <>((Category.apply _).tupled, Category.unapply)

  }

}
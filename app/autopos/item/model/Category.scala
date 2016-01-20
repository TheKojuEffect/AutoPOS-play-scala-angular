package autopos.item.model

import autopos.common.service.repo.DbConfig
import autopos.item.model.Tag._

case class Category(id: Int,
                    shortName: String,
                    name: String)


object Category {

  def readDto(idOpt: Option[Int], shortName: String, name: String) =
    Category(idOpt getOrElse 0, shortName, name)

  import play.api.libs.functional.syntax._
  import play.api.libs.json.Reads.{maxLength, minLength}
  import play.api.libs.json._

  implicit val categoryReads = (
    (__ \ "id").readNullable[Int] ~
      (__ \ "shortName").read(minLength[String](1) andKeep maxLength[String](3)) ~
      (__ \ "name").read(minLength[String](1) andKeep maxLength[String](50))
    ) (readDto _)

  implicit val categoryWrites = Json.writes[Category]

}

trait CategoryDbModule {
  self: DbConfig =>

  import driver.api.{Tag => SlickTag, _}

  protected final lazy val categories = TableQuery[CategoryTable]

  private[CategoryDbModule] class CategoryTable(tag: SlickTag) extends Table[Category](tag, "category") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def shortName = column[String]("short_name", O.Length(3))

    def name = column[String]("name", O.Length(50))

    def * = (id, shortName, name) <>((Category.apply _).tupled, Category.unapply)

  }

}
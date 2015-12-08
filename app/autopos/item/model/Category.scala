package autopos.item.model

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
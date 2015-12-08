package autopos.item.model

case class Tag(id: Int,
               name: String)


object Tag {

  import play.api.libs.functional.syntax._
  import play.api.libs.json.Reads.{maxLength, minLength}
  import play.api.libs.json._


  def readDto(idOpt: Option[Int], name: String) = Tag(idOpt getOrElse 0, name)

  implicit val tagReads = (
    (__ \ "id").readNullable[Int] ~
      (__ \ "name").read(minLength[String](1) andKeep maxLength[String](50))
    ) (readDto _)


  implicit val tagWrites = Json.writes[Tag]

}

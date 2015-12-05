package autopos.item.model


case class Brand(id: Option[Int],
                 name: String)


object Brand {

  import play.api.libs.functional.syntax._
  import play.api.libs.json.Reads.{maxLength, minLength}
  import play.api.libs.json._

  implicit val brandReads = (
    (__ \ "id").readNullable[Int] ~
      (__ \ "name").read(minLength[String](1) andKeep maxLength[String](50))
    ) (Brand.apply _)


  implicit val brandWrites = Json.writes[Brand]
}
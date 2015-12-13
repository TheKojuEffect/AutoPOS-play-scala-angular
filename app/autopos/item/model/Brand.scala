package autopos.item.model

import autopos.common.service.repo.HasDbConfig
import autopos.item.model.Tag._


case class Brand(id: Int,
                 name: String)


object Brand extends HasDbConfig {

  import play.api.libs.functional.syntax._
  import play.api.libs.json.Reads.{maxLength, minLength}
  import play.api.libs.json._

  def readDto(id: Option[Int], name: String) =
    Brand(id getOrElse 0, name)

  implicit val brandReads = (
    (__ \ "id").readNullable[Int] ~
      (__ \ "name").read(minLength[String](1) andKeep maxLength[String](50))
    ) (readDto _)


  implicit val brandWrites = Json.writes[Brand]

  /* ************************************ */


  import driver.api.{Tag => SlickTag, _}

  class BrandTable(tag: SlickTag) extends Table[Brand](tag, "Brand") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name", O.Length(50))

    def * = (id, name) <>((Brand.apply _).tupled, Brand.unapply)

  }

}


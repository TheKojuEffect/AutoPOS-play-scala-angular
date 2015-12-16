package autopos.item.model

import autopos.common.service.repo.HasDbConfig

case class Tag(id: Int,
               name: String)


object Tag extends HasDbConfig {

  import play.api.libs.functional.syntax._
  import play.api.libs.json.Reads.{maxLength, minLength}
  import play.api.libs.json._


  def readDto(idOpt: Option[Int], name: String) = Tag(idOpt getOrElse 0, name)

  implicit val tagReads = (
    (__ \ "id").readNullable[Int] ~
      (__ \ "name").read(minLength[String](1) andKeep maxLength[String](50))
    ) (readDto _)


  implicit val tagWrites = Json.writes[Tag]

  /* ***************************** */

  import driver.api.{Tag => SlickTag, _}

  class TagTable(tag: SlickTag) extends Table[Tag](tag, "tag") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name", O.Length(50))

    def * = (id, name) <>((Tag.apply _).tupled, Tag.unapply)

  }

}

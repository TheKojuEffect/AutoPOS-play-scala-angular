package autopos.item.model

import autopos.shared.service.repo.HasDbConfig
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads.{maxLength, minLength}
import play.api.libs.json._


case class Tag(name: String,
               id: Long = 0)


object Tag {

  implicit val tagReads =
    (__ \ "name").read(minLength[String](1) andKeep maxLength[String](50))
      .map(Tag(_))


  implicit val tagWrites = Json.writes[Tag]

}

trait TagDbModule {
  self: HasDbConfig =>

  import driver.api.{Tag => SlickTag, _}

  protected final lazy val tags = TableQuery[TagTable]

  private[TagDbModule] class TagTable(tag: SlickTag) extends Table[Tag](tag, "tag") {

    def name = column[String]("name", O.Length(50))

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def * = (name, id) <>((Tag.apply _).tupled, Tag.unapply)

  }

}

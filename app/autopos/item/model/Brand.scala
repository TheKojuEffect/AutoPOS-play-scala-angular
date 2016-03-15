package autopos.item.model

import java.time.LocalDateTime
import java.time.LocalDateTime.now

import autopos.shared.model.Audited
import autopos.shared.service.repo.HasDbConfig
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads.{maxLength, minLength}
import play.api.libs.json._


case class Brand(name: String,
                 id: Long = 0,
                 createdOn: LocalDateTime = now,
                 lastUpdatedOn: LocalDateTime = now)
  extends Audited


object Brand {

  implicit val brandReads =
    (__ \ "name").read(minLength[String](1) andKeep maxLength[String](50))
      .map(Brand(_))

  val brandIdReads = (__ \ "id").read[Long]
    .map(Brand("", _))

  implicit val brandWrites = Json.writes[Brand]

}


trait BrandDbModule {
  self: HasDbConfig =>

  import driver.api.{Tag => SlickTag, _}

  protected final lazy val brands = TableQuery[BrandTable]

  private[BrandDbModule] class BrandTable(tag: SlickTag) extends Table[Brand](tag, "brand") {

    def name = column[String]("name", O.Length(50))

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def createdOn = column[LocalDateTime]("created_on")

    def lastUpdatedOn = column[LocalDateTime]("last_updated_on")

    def * = (name, id, createdOn, lastUpdatedOn) <>((Brand.apply _).tupled, Brand.unapply)

  }

}
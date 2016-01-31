package autopos.item.model

import autopos.common.service.repo.DbConfig
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads.{maxLength, minLength}
import play.api.libs.json._


case class Brand(name: String,
                 id: Int = 0)


object Brand {

  implicit val brandReads =
    (__ \ "name").read(minLength[String](1) andKeep maxLength[String](50))
      .map(Brand(_))

  val brandIdReads = (__ \ "id").read[Int]
    .map(Brand("", _))

  implicit val brandWrites = Json.writes[Brand]

}


trait BrandDbModule {
  self: DbConfig =>

  import driver.api.{Tag => SlickTag, _}

  protected final lazy val brands = TableQuery[BrandTable]

  private[BrandDbModule] class BrandTable(tag: SlickTag) extends Table[Brand](tag, "brand") {

    def name = column[String]("name", O.Length(50))

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def * = (name, id) <>((Brand.apply _).tupled, Brand.unapply)

  }

}
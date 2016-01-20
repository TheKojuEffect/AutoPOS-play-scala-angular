package autopos.item.model

import autopos.common.service.repo.DbConfig


case class Brand(id: Int,
                 name: String)


object Brand {

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

}


trait BrandDbModule {
  self: DbConfig =>

  import driver.api.{Tag => SlickTag, _}

  protected final lazy val brands = TableQuery[BrandTable]

  private[BrandDbModule] class BrandTable(tag: SlickTag) extends Table[Brand](tag, "brand") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name", O.Length(50))

    def * = (id, name) <>((Brand.apply _).tupled, Brand.unapply)

  }

}
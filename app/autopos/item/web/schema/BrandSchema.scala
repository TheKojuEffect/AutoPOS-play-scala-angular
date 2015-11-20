package autopos.item.web.schema

import play.api.libs.json.Json

case class BrandDto(name: String)

object BrandDto {
  implicit val brandDtoReader = Json.reads[BrandDto]
}

case class BrandResource(id: Long, name: String)

object BrandResource {
  implicit val brandResourceWriter = Json.writes[BrandResource]
}

package autopos.item.web.schema

import play.api.libs.json.Json

case class TagDto(name: String)

object TagDto {
  implicit val brandDtoReader = Json.reads[TagDto]
}

case class TagResource(id: Long, name: String)

object TagResource {
  implicit val brandResourceWriter = Json.writes[TagResource]
}

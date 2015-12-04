package autopos.item.web.schema

import play.api.libs.json.Json

case class CategoryDto(shortName: String,
                       name: String)

object CategorySchema {
  implicit val categoryReader = Json.reads[CategoryDto]
}


case class CategoryResource(id: Int,
                            shortName: String,
                            name: String)

object CategoryResource {
  implicit val categoryWriter = Json.writes[CategoryResource]
}
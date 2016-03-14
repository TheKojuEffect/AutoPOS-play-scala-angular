package autopos.user.web.schema

import autopos.user.model.Role.Role
import play.api.libs.json.Json

case class UserResource(id: Long,
                        username: String,
                        firstName: String,
                        lastName: String,
                        roles: Set[Role])

object UserResource {

  implicit val userResourceWriter = Json.writes[UserResource]

}


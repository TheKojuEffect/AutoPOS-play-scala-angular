package autopos.user.model

import autopos.user.model.Role.Role
import play.api.libs.json.Json

case class Authority(role: Role)


object Authority {

  implicit val authorityWriter = Json.writes[Authority]

}


object Role extends Enumeration {
  type Role = Value
  val ADMIN, USER = Value
}


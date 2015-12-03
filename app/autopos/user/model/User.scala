package autopos.user.model

import play.api.libs.json.Json

case class User(id: Int,
                username: String,
                password: String,
                authorities: Set[Authority],
                firstName: String,
                lastName: String,
                activated: Boolean,
                activationKey: String)


object User {

  implicit val userWriter = Json.writes[User]

}
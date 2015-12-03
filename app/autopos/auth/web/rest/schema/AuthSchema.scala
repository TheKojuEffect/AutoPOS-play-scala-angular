package autopos.auth.web.rest.schema

import play.api.libs.json.Json

case class Credential(username: String, password: String)

object Credential {
  implicit val credentialReader = Json.reads[Credential]
}

case class AuthToken(token: String, expires: Long)

object AuthToken {
  implicit val authTokenWriter = Json.writes[AuthToken]
}
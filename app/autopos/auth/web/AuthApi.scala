package autopos.auth.web

import autopos.auth.UserDetails
import autopos.auth.service.TokenProvider
import autopos.auth.web.schema.Credential
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{Action, Controller}

class AuthApi extends Controller {

  def authenticate = Action(parse.json) { request =>
    val credential = request.body.validate[Credential]
    credential.fold(
      errors => {
        BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toJson(errors)))
      },
      auth => {
        val userDetails: UserDetails = UserDetails(auth.username, auth.password)
        val token = TokenProvider createToken userDetails
        Ok(Json toJson token)
      }
    )
  }
}

package autopos.auth.web

import javax.inject.Inject

import autopos.user.service.UserService
import autopos.user.web.assembler.UserAssembler
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

class AccountApi @Inject()(userService: UserService, userAssembler: UserAssembler) extends Controller {

  def getAccount(username: String) = Action {
    userService.getUser(username)
      .map {
        user =>
          val userResource = userAssembler assemble user
          Ok(Json toJson userResource)
      }
      .getOrElse(NotFound)
  }

}

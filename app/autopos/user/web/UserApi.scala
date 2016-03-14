package autopos.user.web

import javax.inject.Inject

import autopos.user.service.UserService
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

class UserApi @Inject()(userService: UserService) extends Controller {

  def getUser(id: Long) = Action {
    userService.getUser(id)
      .map(user => Ok(Json.toJson(user)))
      .getOrElse(NotFound)
  }

}

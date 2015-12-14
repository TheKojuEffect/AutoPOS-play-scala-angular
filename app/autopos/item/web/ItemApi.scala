package autopos.item.web

import javax.inject.Inject

import autopos.item.service.ItemService
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext.Implicits.global

class ItemApi @Inject()(itemService: ItemService)
  extends Controller {


  def getItems() = Action.async {
    itemService.getItems()
      .map { items =>
        Ok(Json.toJson(items))
      }
  }

}

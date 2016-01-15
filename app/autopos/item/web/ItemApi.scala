package autopos.item.web

import javax.inject.Inject

import autopos.item.model.Item
import autopos.item.service.ItemService
import play.api.libs.json.Json.toJson
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ItemApi @Inject()(itemService: ItemService)
  extends Controller {


  def getItems() = Action.async {
    itemService.getItems()
      .map { items =>
        Ok(toJson(items))
      }
  }

  def getItem(id: Int) = Action.async {
    itemService.getItem(id)
      .map {
        _.map { item =>
          Ok(toJson(item))
        } getOrElse BadRequest
      }
  }

  def updateItem(id: Int) = Action.async(parse.json) { request =>
    request.body.validate[Item]
      .fold(
        errors => {
          Future.successful(BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors))))
        },
        itemDto => {
          itemService.updateItem(itemDto.copy(id = id))
            .map { _ =>
              Accepted
            }
        }
      )
  }


  def addItem = Action.async(parse.json) { request =>
    request.body.validate[Item]
      .fold(
        errors => {
          Future.successful(BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors))))
        },
        itemDto => {
          itemService.addItem(itemDto)
            .map { item =>
              Ok(toJson(item))
            }
        }
      )
  }

  def deleteItem(id: Int) = Action.async {
    itemService.deleteItem(id)
      .map {
        _ => NoContent
      }
  }
}

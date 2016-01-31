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
        case Some(item) => Ok(toJson(item))
        case None => NotFound
      }
  }

  def updateItem(id: Int) = Action.async(parse.json) { request =>
    request.body.validate[Item]
      .fold(
        errors => {
          Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors))))
        },
        item => {
          itemService.updateItem(item.copy(id = id))
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
          Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors))))
        },
        item => {
          itemService.addItem(item)
            .map { itemId =>
              Created(Json.obj("id" -> itemId))
                .withHeaders(LOCATION -> s"/api/items/$itemId")
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

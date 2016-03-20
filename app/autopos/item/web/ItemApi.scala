package autopos.item.web

import javax.inject.Inject

import autopos.item.model.ItemCommand
import autopos.item.service.ItemService
import autopos.shared.structure.Pageable
import play.api.libs.json.Json.toJson
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ItemApi @Inject()(itemService: ItemService)
  extends Controller {

  def getItems(pageable: Pageable) = Action.async {
    itemService.getItems(pageable)
      .map { itemsPage =>
        Ok(toJson(itemsPage))
      }
  }

  def getItem(id: Long) = Action.async {
    itemService.getItem(id)
      .map {
        case Some(item) => Ok(toJson(item))
        case None => NotFound
      }
  }

  def updateItem(id: Long) = Action.async(parse.json) { request =>
    request.body.validate[ItemCommand]
      .fold(
        errors => {
          Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors))))
        },
        itemCommand => {
          itemService.updateItem(id, itemCommand)
            .map { _ =>
              Accepted
            }
        }
      )
  }

  def addItem() = Action.async(parse.json) { request =>
    request.body.validate[ItemCommand]
      .fold(
        errors => {
          Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors))))
        },
        itemCommand => {
          itemService.addItem(itemCommand)
            .map { itemId =>
              Created(Json.obj("id" -> itemId))
                .withHeaders(LOCATION -> s"/api/items/$itemId")
            }
        }
      )
  }

  def deleteItem(id: Long) = Action.async {
    itemService.deleteItem(id)
      .map {
        _ => NoContent
      }
  }
}

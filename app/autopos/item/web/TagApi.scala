package autopos.item.web

import javax.inject.Inject

import autopos.item.model.Tag
import autopos.item.service.repo.TagRepo
import play.api.libs.json.Json.toJson
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{Action, Controller}

import scala.concurrent.{ExecutionContext, Future}

class TagApi @Inject()(tagRepo: TagRepo)
                      (implicit ec: ExecutionContext)
  extends Controller {

  def getTags = Action.async {
    tagRepo.list()
      .map {
        tags => Ok(toJson(tags))
      }
  }

  def getTag(id: Int) = Action.async {
    tagRepo.findById(id)
      .map {
        tagOption => tagOption map {
          tag => Ok(toJson(tag))
        } getOrElse NotFound
      }
  }

  def addTag = Action.async(parse.json) { request =>
    request.body.validate[Tag]
      .fold(
        errors => {
          Future.successful(
            BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors)))
          )
        },
        tagDto => {
          tagRepo.create(tagDto)
            .map {
              tag => Ok(toJson(tag))
            }
        }
      )
  }

  def updateTag(id: Int) = Action.async(parse.json) { request =>
    request.body.validate[Tag]
      .fold(
        errors => {
          Future.successful(
            BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors)))
          )
        }, tag => {
          tagRepo.update(tag.copy(id = id))
            .map {
              _ => Accepted
            }
        })
  }

  def deleteTag(id: Int) = Action.async {
    tagRepo.delete(id)
      .map {
        _ => NoContent
      }
  }
}

package autopos.item.web

import javax.inject.Inject

import autopos.item.model.Category
import autopos.item.service.repo.CategoryRepo
import play.api.libs.json.Json.toJson
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{Action, Controller}

import scala.concurrent.{ExecutionContext, Future}

class CategoryApi @Inject()(categoryRepo: CategoryRepo)
                           (implicit ec: ExecutionContext)
  extends Controller {

  def getCategories = Action.async {
    categoryRepo.list()
      .map { categories =>
        Ok(toJson(categories))
      }
  }

  def getCategory(id: Int) = Action.async {
    categoryRepo.findById(id)
      .map { categoryOption =>
        categoryOption map { category =>
          Ok(toJson(category))
        } getOrElse NotFound
      }
  }

  def updateCategory(id: Int) = Action.async(parse.json) { request =>
    request.body.validate[Category]
      .fold(
        errors => {
          Future.successful(
            BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors)))
          )
        },
        category => {
          categoryRepo.update(category.copy(id = id))
            .map { _ =>
              Accepted
            }
        }
      )
  }


  def addCategory = Action.async(parse.json) { request =>
    request.body.validate[Category]
      .fold(
        errors => {
          Future.successful(
            BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors)))
          )
        },
        categoryDto => {
          categoryRepo.create(categoryDto)
            .map { category =>
              Ok(toJson(category))
            }
        }
      )
  }

  def deleteCategory(id: Int) = Action.async {
    categoryRepo.delete(id)
      .map { _ =>
        NoContent
      }
  }
}

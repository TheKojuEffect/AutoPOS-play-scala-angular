package autopos.item.web

import javax.inject.Inject

import autopos.item.model.Brand
import autopos.item.service.repo.BrandRepo
import play.api.libs.json.Json.toJson
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{Action, Controller}

import scala.concurrent.{ExecutionContext, Future}

class BrandApi @Inject()(brandRepo: BrandRepo)
                        (implicit ec: ExecutionContext)
  extends Controller {

  def getBrands = Action.async {
    brandRepo.list()
      .map {
        brands => Ok(toJson(brands))
      }
  }

  def addBrand = Action.async(parse.json) { request =>
    request.body.validate[Brand]
      .fold(
        errors => {
          Future.successful(
            BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors)))
          )
        },
        brandDto => {
          brandRepo.create(brandDto)
            .map {
              brand =>
                Created(toJson(brand))
            }
        }
      )
  }

  def getBrand(id: Long) = Action.async {
    brandRepo.findById(id)
      .map {
        brandOption => brandOption.map {
          brand =>
            Ok(toJson(brand))
        }.getOrElse(NotFound)
      }
  }


  def updateBrand(id: Long) = Action.async(parse.json) { request =>
    request.body.validate[Brand]
      .fold(
        errors => {
          Future.successful(
            BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors)))
          )
        },
        brand => {
          brandRepo.update(brand.copy(id = id))
            .map {
              _ => Accepted
            }
        }
      )
  }

  def deleteBrand(id: Long) = Action.async {
    brandRepo.delete(id)
      .map {
        _ => NoContent
      }
  }
}

package autopos.item.web

import javax.inject.Inject

import autopos.item.model.Brand
import autopos.item.service.BrandRepo
import play.api.libs.json.Json.toJson
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{Action, Controller}

import scala.concurrent.{Future, ExecutionContext}

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
                Ok(toJson(brand))
            }
        }
      )
  }
}

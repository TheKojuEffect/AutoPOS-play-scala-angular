package autopos.item.web

import javax.inject.Inject

import autopos.item.model.Brand
import autopos.item.service.BrandRepo
import play.api.libs.json.Json.toJson
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext

class BrandApi @Inject()(brandRepo: BrandRepo)
                        (implicit ec: ExecutionContext)
  extends Controller {

  def getBrands = Action.async {
    brandRepo.list()
      .map {
        brands => Ok(toJson(brands))
      }
  }

  def addBrand = Action(parse.json) { request =>

    val b = request.body.validate[Brand]
    b.fold(
      errors => {
        BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors)))
      },
      brandDto => {
        brandRepo.create(brandDto.name)
        Ok(Json.obj("status" -> "OK"))
      }
    )
  }
}

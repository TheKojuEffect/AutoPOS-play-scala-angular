package autopos.item.web

import javax.inject.Inject

import autopos.item.service.BrandRepo
import autopos.item.web.assembler.BrandAssembler
import autopos.item.web.schema.BrandDto
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext

class BrandApi @Inject()(brandRepo: BrandRepo, brandAssembler: BrandAssembler)
                        (implicit ec: ExecutionContext)
  extends Controller {

  def getBrands = Action.async {
    brandRepo.list()
      .map {
        brandAssembler.assemble
      }
      .map {
        brandResources => Ok(Json.toJson(brandResources))
      }
  }

  def addBrand = Action(parse.json) { request =>

    val b = request.body.validate[BrandDto]
    b.fold(
      errors => {
        BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toJson(errors)))
      },
      brandDto => {
        brandRepo.create(brandDto.name)
        Ok(Json.obj("status" -> "OK"))
      }
    )
  }

}

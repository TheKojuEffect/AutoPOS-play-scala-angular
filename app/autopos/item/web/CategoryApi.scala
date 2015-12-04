package autopos.item.web

import javax.inject.Inject

import autopos.item.service.CategoryRepo
import autopos.item.web.assembler.CategoryAssembler
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext

class CategoryApi @Inject()(categoryRepo: CategoryRepo,
                            categoryAssembler: CategoryAssembler)
                           (implicit ec: ExecutionContext)
  extends Controller {

  def getCategories = Action.async {
    categoryRepo.list()
      .map {
        categoryAssembler.assemble
      }
      .map {
        categoryResources => Ok(Json toJson categoryResources)
      }
  }
}

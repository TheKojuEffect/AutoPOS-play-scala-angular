package autopos.item.web

import javax.inject.Inject

import autopos.item.service.TagRepo
import autopos.item.web.assembler.TagAssembler
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext

class TagApi @Inject()(tagsRepo: TagRepo,
                       tagsAssembler: TagAssembler)
                      (implicit ec: ExecutionContext)
  extends Controller {

  def getTags = Action.async {
    tagsRepo.list()
      .map {
        tagsAssembler.assemble
      }
      .map {
        tagsResources => Ok(Json toJson tagsResources)
      }
  }
}

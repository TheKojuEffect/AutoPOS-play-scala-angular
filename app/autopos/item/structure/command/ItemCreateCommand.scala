package autopos.item.structure.command

import autopos.item.model.Item
import autopos.item.model.ItemDto._
import autopos.shared.web.format.CommonReads._
import play.api.libs.functional.syntax._

case class ItemCreateCommand(name: String,
                             markedPrice: BigDecimal,
                             categoryId: Option[Long],
                             brandId: Option[Long],
                             quantity: Int,
                             description: Option[String],
                             location: Option[String],
                             remarks: Option[String]) {

  def toItem =
    Item(name, description, location, remarks, markedPrice, categoryId, brandId)
}


object ItemCreateCommand {

  implicit val itemCreateCommandReads = (
    nameReads ~
      markedPriceReads ~
      optionalId("categoryId") ~
      optionalId("brandId") ~
      requiredInt("quantity") ~
      descriptionReads ~
      locationReads ~
      remarksReads
    ) (ItemCreateCommand.apply _)


}

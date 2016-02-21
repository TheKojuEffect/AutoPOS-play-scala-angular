package autopos.shared.structure

import play.api.libs.json.Json.{obj, toJson}
import play.api.libs.json._

trait Page[T] {

  def elements: Seq[T]

  def totalElements: Int

  def pageable: Pageable

  def totalPages: Int =
    math.ceil(totalElements.toDouble / pageable.pageSize.toDouble).toInt

}

object Page {

  implicit def pageWrites[T](implicit fmt: Writes[T]): Writes[Page[T]] = new Writes[Page[T]] {
    def writes(p: Page[T]) = obj(
      "elements" -> p.elements.map(toJson(_)),
      "metadata" -> obj(
        "page" -> p.pageable.pageNumber,
        "size" -> p.pageable.pageSize,
        "totalElements" -> p.totalElements,
        "totalPages" -> p.totalPages
      )
    )
  }
}
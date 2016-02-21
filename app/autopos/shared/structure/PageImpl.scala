package autopos.shared.structure

case class PageImpl[T](elements: Seq[T],
                       totalElements: Int,
                       pageable: Pageable)
  extends Page[T]
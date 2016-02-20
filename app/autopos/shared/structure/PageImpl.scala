package autopos.shared.structure

case class PageImpl[T](content: Seq[T],
                       totalElements: Int,
                       pageable: Pageable)
  extends Page[T]
package autopos.shared.structure

case class PageImpl[T](content: Seq[T],
                       pageable: Pageable,
                       totalElements: Int)
  extends Page[T]
package autopos.shared.web

case class PagedResoures[T](content: Iterable[T], pageMetaData: PageMetaData, links: Seq[Link])

case class PageMetaData(number: Int, size: Int, totalElements: Int, totalPages: Int)

case class Link(rel: String, href: String)

object Link {
  val SELF = "self"
  val FIRST = "first"
  val PREVIOUS = "prev"
  val NEXT = "next"
  val LAST = "last"
}


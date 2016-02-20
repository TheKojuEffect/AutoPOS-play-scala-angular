package autopos.shared.web


case class PagedResources[T](content: Iterable[T],
                             pageMeta: PageMeta,
                             links: Seq[Link])

case class PageMeta(number: Int,
                    size: Int,
                    totalElements: Int,
                    totalPages: Int)

case class Link(rel: String,
                href: String)

object Link {
  val SELF = "self"
  val FIRST = "first"
  val PREVIOUS = "prev"
  val NEXT = "next"
  val LAST = "last"
}


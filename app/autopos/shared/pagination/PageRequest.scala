package autopos.shared.pagination

case class PageRequest(pageNumber: Int, pageSize: Int) extends Pageable {

  override def sort: Option[Sort] = None

}

package autopos.shared.structure

trait Pageable {

  require(pageNumber >= 1, "Page Number starts with 1")
  require(pageSize > 0, "Page Size should be positive number")

  def pageNumber: Int

  def pageSize: Int

  final def offset: Int =
    (pageNumber - 1) * pageSize

  def sortable: Option[Sortable]

  def searchable: Option[Searchable]

}

package autopos.shared.structure

trait Page[T] {

  def content: Seq[T]

  def totalElements: Int

  def pageable: Pageable

}

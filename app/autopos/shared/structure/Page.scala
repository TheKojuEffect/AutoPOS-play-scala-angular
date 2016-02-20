package autopos.shared.structure

trait Page[T] {

  def content: Seq[T]

  def pageable: Pageable

  def totalElements: Int

}

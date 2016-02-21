package autopos.shared.structure

import play.api.mvc.QueryStringBindable

case class Pager(pageNumber: Int, pageSize: Int,
                 sortable: Option[Sortable] = None,
                 searchable: Option[Searchable] = None)
  extends Pageable


object Pager {

  implicit def pagerBinder(implicit intBinder: QueryStringBindable[Int]) = new QueryStringBindable[Pager] {

    override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, Pager]] = {

      val pageNumber = intBinder.bind("page", params).getOrElse(Right(1)).right.get
      val pageSize = intBinder.bind("size", params).getOrElse(Right(20)).right.get

      Some(Right(Pager(pageNumber, pageSize)))
    }

    override def unbind(key: String, pager: Pager): String = {
      intBinder.unbind("page", pager.pageNumber) + "&" + intBinder.unbind("size", pager.pageSize)
    }
  }
}
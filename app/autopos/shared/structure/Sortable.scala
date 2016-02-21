package autopos.shared.structure

import autopos.shared.structure.Sortable.Order

trait Sortable {

  def orders: Seq[Order]

}

object Sortable {

  case class Order(property: String,
                   direction: Direction = Direction.ASC,
                   ignoreCase: Boolean = false,
                   nullHandling: NullHandling = NullHandling.NATIVE)


  sealed trait Direction

  object Direction {

    case object ASC extends Direction

    case object DESC extends Direction

  }


  sealed trait NullHandling

  object NullHandling {

    case object NATIVE extends NullHandling

    case object NULLS_FIRST extends NullHandling

    case object NULLS_LAST extends NullHandling

  }

}
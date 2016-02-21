package autopos.shared.structure

import autopos.shared.structure.Sortable.Order

case class Sort(orders: Seq[Order])
  extends Sortable
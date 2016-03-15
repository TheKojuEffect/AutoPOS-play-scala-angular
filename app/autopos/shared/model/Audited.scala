package autopos.shared.model

import java.time.LocalDateTime

trait Audited {

  def createdOn: LocalDateTime

  def lastUpdatedOn: LocalDateTime
}

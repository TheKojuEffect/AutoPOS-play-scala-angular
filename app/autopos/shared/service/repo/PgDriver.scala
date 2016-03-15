package autopos.shared.service.repo

import com.github.tminglei.slickpg._
import slick.driver.PostgresDriver

trait PgDriver extends PostgresDriver
  with PgDate2Support {

  override val api = new API
    with DateTimeImplicits {}
}

object PgDriver extends PgDriver


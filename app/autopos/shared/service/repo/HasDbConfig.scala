package autopos.shared.service.repo

import play.api.db.slick.HasDatabaseConfig

trait HasDbConfig
  extends HasDatabaseConfig[PgDriver] {
}

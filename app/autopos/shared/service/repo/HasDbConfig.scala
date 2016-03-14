package autopos.shared.service.repo

import play.api.db.slick.HasDatabaseConfig
import slick.driver.JdbcProfile

trait HasDbConfig extends HasDatabaseConfig[JdbcProfile] {
}

package autopos.shared.service.repo

import play.api.Play
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import slick.driver.JdbcProfile

trait DbConfig extends HasDatabaseConfig[JdbcProfile] {

  protected final lazy val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

}

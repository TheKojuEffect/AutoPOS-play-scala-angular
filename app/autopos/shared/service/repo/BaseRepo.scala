package autopos.shared.service.repo

import play.api.db.slick.DatabaseConfigProvider
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile

trait BaseRepo extends HasDbConfig

abstract class BaseRepoImpl(dbConfigProvider: DatabaseConfigProvider)
  extends BaseRepo {

  override protected final lazy val dbConfig: DatabaseConfig[JdbcProfile] = dbConfigProvider.get[JdbcProfile]
}
package autopos.shared.service.repo

import play.api.db.slick.DatabaseConfigProvider

trait BaseRepo extends HasDbConfig

abstract class BaseRepoImpl(dbConfigProvider: DatabaseConfigProvider)
  extends BaseRepo {

  override protected final lazy val dbConfig = dbConfigProvider.get[PgDriver]
}
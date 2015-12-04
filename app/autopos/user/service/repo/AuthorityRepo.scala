package autopos.user.service.repo

import javax.inject.Inject

import autopos.user.model.Authority
import autopos.user.model.Role.Role
import com.google.inject.ImplementedBy

import scala.concurrent.{ExecutionContext, Future}

@ImplementedBy(classOf[AuthorityRepoImpl])
private[service] trait AuthorityRepo {

  def findByRole(role: Role): Future[Option[Authority]]

}

private[repo] class AuthorityRepoImpl @Inject()(implicit ec: ExecutionContext)
  extends AuthorityRepo {

  override def findByRole(role: Role) =
    Future {
      Option(Authority(role))
    }
}

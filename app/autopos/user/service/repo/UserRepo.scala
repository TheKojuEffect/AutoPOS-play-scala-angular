package autopos.user.service.repo

import autopos.user.model.Role._
import autopos.user.model.{Authority, User}
import com.google.inject.ImplementedBy

@ImplementedBy(classOf[UserRepoImpl])
private[service] trait UserRepo {

  def get(id: Int): Option[User]

  def findByUsername(username: String): Option[User]

}

private[repo] class UserRepoImpl extends UserRepo {

  val users = Seq(
    User(1, "chuck", "norris", Set(Authority(ROLE_ADMIN)), "Chuck", "Norris", true, "chuck_norris"),
    User(2, "john", "doe", Set(Authority(ROLE_USER)), "John", "Doe", true, "john_doe")
  )

  override def get(id: Int): Option[User] =
    users.filter(_.id == id).headOption

  override def findByUsername(username: String): Option[User] =
    users.filter(_.username == username).headOption
}

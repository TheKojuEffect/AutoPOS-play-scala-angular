package autopos.user.service.repo

import autopos.user.model.Role._
import autopos.user.model.{Authority, User}
import com.google.inject.ImplementedBy

@ImplementedBy(classOf[UserRepoImpl])
trait UserRepo {

  def get(id: Int): Option[User]

}

class UserRepoImpl extends UserRepo {

  val users = Seq(
    User(1, "chuck", "norris", Set(Authority(ADMIN)), "Chuck", "Norris", true, "chuck_norris"),
    User(2, "john", "doe", Set(Authority(USER)), "John", "Doe", true, "john_doe")
  )

  override def get(id: Int): Option[User] =
    users.filter(_.id == id).headOption
}
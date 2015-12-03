package autopos.user.service

import javax.inject.Inject

import autopos.user.model.User
import autopos.user.service.repo.UserRepo
import com.google.inject.ImplementedBy

@ImplementedBy(classOf[UserServiceImpl])
trait UserService {

  def getUser(id: Int): Option[User]

  def getUser(username: String): Option[User]

}


class UserServiceImpl @Inject()(userRepo: UserRepo) extends UserService {

  override def getUser(id: Int): Option[User] = userRepo.get(id)

  override def getUser(username: String): Option[User] = userRepo.findByUsername(username)
}

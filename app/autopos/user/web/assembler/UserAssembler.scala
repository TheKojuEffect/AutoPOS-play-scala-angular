package autopos.user.web.assembler

import javax.inject.Singleton

import autopos.user.model.User
import autopos.user.web.schema.UserResource
import com.google.inject.ImplementedBy

@ImplementedBy(classOf[UserAssemblerImpl])
trait UserAssembler {

  def assemble(user: User): UserResource

}

@Singleton
class UserAssemblerImpl extends UserAssembler {

  override def assemble(u: User) =
    UserResource(u.id, u.username, u.firstName, u.lastName, u.authorities.map(_.role))
  
}



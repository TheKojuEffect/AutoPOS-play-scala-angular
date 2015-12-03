package autopos.user.service.repo

import com.google.inject.ImplementedBy

@ImplementedBy(classOf[AuthorityRepoImpl])
trait AuthorityRepo {

}

class AuthorityRepoImpl extends AuthorityRepo {

}

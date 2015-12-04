package autopos.item.service

import javax.inject.{Inject, Singleton}

import autopos.item.model.Tag
import com.google.inject.ImplementedBy

import scala.concurrent.{ExecutionContext, Future}


@ImplementedBy(classOf[TagRepoImpl])
trait TagRepo {

  def list(): Future[Seq[Tag]]

}

@Singleton
class TagRepoImpl @Inject()(implicit ed: ExecutionContext)
  extends TagRepo {

  val tags = Seq(
    Tag(1, "Engine"),
    Tag(2, "Gear")
  )

  override def list(): Future[Seq[Tag]] =
    Future {
      tags
    }

}

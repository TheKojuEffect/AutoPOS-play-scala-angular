package autopos.item.service

import javax.inject.{Inject, Singleton}

import autopos.item.model.Category
import com.google.inject.ImplementedBy

import scala.concurrent.{ExecutionContext, Future}


@ImplementedBy(classOf[CategoryRepoImpl])
trait CategoryRepo {

  def list(): Future[Seq[Category]]

}

@Singleton
class CategoryRepoImpl @Inject()(implicit ed: ExecutionContext)
  extends CategoryRepo {

  val categories = Seq(
    Category(1, "MN", "Mahindra Nissan"),
    Category(2, "SM", "Swaraj Mazda")
  )

  override def list(): Future[Seq[Category]] =
    Future {
      categories
    }

}

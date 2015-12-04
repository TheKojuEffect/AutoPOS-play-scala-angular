package autopos.item.web.assembler

import javax.inject.Singleton

import autopos.item.model.Category
import autopos.item.web.schema.CategoryResource
import com.google.inject.ImplementedBy


@ImplementedBy(classOf[CategoryAssemblerImpl])
trait CategoryAssembler {

  def assemble(category: Category): CategoryResource

  def assemble(categories: Seq[Category]): Seq[CategoryResource]
}


@Singleton
class CategoryAssemblerImpl extends CategoryAssembler {

  override def assemble(categories: Seq[Category]): Seq[CategoryResource] =
    categories map assemble

  override def assemble(c: Category) =
    CategoryResource(c.id, c.shortName, c.name)

}


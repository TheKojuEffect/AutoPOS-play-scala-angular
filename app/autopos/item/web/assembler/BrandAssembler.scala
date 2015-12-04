package autopos.item.web.assembler

import javax.inject.Singleton

import autopos.item.model.Brand
import autopos.item.web.schema.BrandResource
import com.google.inject.ImplementedBy

@ImplementedBy(classOf[BrandAssemblerImpl])
trait BrandAssembler {

  def assemble(brand: Brand): BrandResource

  def assemble(brands: Seq[Brand]): Seq[BrandResource]
}


@Singleton
class BrandAssemblerImpl extends BrandAssembler {

  override def assemble(brands: Seq[Brand]): Seq[BrandResource] =
    brands map assemble

  override def assemble(b: Brand) =
    BrandResource(b.id, b.name)

}

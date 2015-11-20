package autopos.item.web.assembler

import javax.inject.Singleton

import autopos.item.model.Brand
import autopos.item.web.schema.BrandResource

@Singleton
class BrandAssembler {

  def assemble(brand: Brand) =
    new BrandResource(id = brand.id, name = brand.name)

  def assemble(brands: Seq[Brand]): Seq[BrandResource] = brands.map(assemble)

}

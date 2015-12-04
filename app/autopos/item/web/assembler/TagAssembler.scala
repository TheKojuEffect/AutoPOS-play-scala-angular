package autopos.item.web.assembler

import javax.inject.Singleton

import autopos.item.model.Tag
import autopos.item.web.schema.TagResource
import com.google.inject.ImplementedBy


@ImplementedBy(classOf[TagAssemblerImpl])
trait TagAssembler {

  def assemble(tag: Tag): TagResource

  def assemble(tags: Seq[Tag]): Seq[TagResource]
}


@Singleton
class TagAssemblerImpl extends TagAssembler {

  override def assemble(tags: Seq[Tag]): Seq[TagResource] =
    tags map assemble

  override def assemble(t: Tag) =
    TagResource(t.id, t.name)

}


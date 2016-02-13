import {Component} from "angular2/core";
import {TagService} from "./tag_service";
import {Tag} from "./tag";
import {AddTag} from "./add_tag";
import {ROUTER_DIRECTIVES} from "angular2/router";


@Component({
  selector: "tag-list",
  templateUrl: "./app/catalog/tags/tag_list.html",
  directives: <any>[ROUTER_DIRECTIVES, AddTag]
})

export class TagList {

  tags:Array<Tag>;

  constructor(private tagService:TagService) {
    tagService.getTags()
      .subscribe(res => this.tags = res.json());
  }

}

import {Component} from "angular2/core";
import {ROUTER_DIRECTIVES} from "angular2/router";
import {TagService} from "./tag_service";
import {Tag} from "./tag";
import {Router} from "angular2/router";

@Component({
  selector: "add-tag",
  templateUrl: "./app/catalog/tags/tag_form.html",
  directives: <any>[ROUTER_DIRECTIVES]
})

export class AddTag {

  title = "Add Tag";

  tag = new Tag(null, null);

  constructor(private tagService:TagService,
              private router:Router) {
  }

  onSubmit() {
    this.tagService.addTag(this.tag)
      .subscribe(res => {
        this.tag = res.json();
        this.router.navigate(["TagDetail", {id: this.tag.id}]);
      });
  }

}

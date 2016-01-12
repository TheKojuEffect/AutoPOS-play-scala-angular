import {Component} from "angular2/core";
import {RouterLink} from "angular2/router";
import {OnInit} from "angular2/core";
import {RouteParams} from "angular2/router";
import {TagService} from "./tag_service";
import {Tag} from "./tag";
import {Router} from "angular2/router";

@Component({
  selector: "edit-tag",
  templateUrl: "./app/catalog/tags/tag_form.html",
  directives: <any>[RouterLink]
})

export class EditTag implements OnInit {

  title = "Edit Tag";

  tag:Tag;

  constructor(private tagService:TagService,
              private routeParams:RouteParams,
              private router:Router) {

  }

  ngOnInit() {
    let id = this.routeParams.get("id");
    this.tagService.getTag(parseInt(id))
      .subscribe(tag => {
        this.tag = tag.json();
        this.title += " [" + this.tag.name + "]";
      });
  }

  onSubmit() {
    this.tagService.updateTag(this.tag)
      .subscribe(()  => {
          this.router.navigate(["TagDetail", {id: this.tag.id}]);
        }
      );
  }
}

import {Component} from "angular2/core";
import {TagService} from "./tag_service";
import {Router} from "angular2/router";
import {RouteParams} from "angular2/router";
import {OnInit} from "angular2/core";
import {Tag} from "./tag";
import {RouterLink} from "angular2/router";
import {Modal} from "angular2-modal/dist/angular2-modal";
import {YesNoModal} from "angular2-modal/dist/angular2-modal";
import {Injector} from "angular2/core";
import {YesNoModalContent} from "angular2-modal/dist/angular2-modal";
import {ICustomModal} from "angular2-modal/dist/angular2-modal";
import {provide} from "angular2/core";

@Component({
  selector: "tag-detail",
  templateUrl: "./app/catalog/tags/tag_detail.html",
  directives: <any>[RouterLink],
  providers: [Modal]
})

export class TagDetail implements OnInit {

  tag:Tag;

  constructor(private tagService:TagService,
              private routeParams:RouteParams,
              private router:Router,
              private modal:Modal) {
  }


  ngOnInit() {
    let id = this.routeParams.get("id");
    this.tagService.getTag(parseInt(id))
      .subscribe(tag => this.tag = tag.json());
  }

  deleteTag() {
    let bindings = Injector.resolve([
      provide(ICustomModal,
        {useValue: new YesNoModalContent("Delete Tag?", "Are you sure you want to delete this tag?", false, "Yes", "No")})
    ]);

    this.modal.open(<any>YesNoModal, bindings)
      .then(r => r.result.then(re => {
        if (re) {
          this.confirmDeleteTag();
        }
      }));
  }

  private confirmDeleteTag() {
    this.tagService.deleteTag(this.tag.id)
      .subscribe(() => this.router.navigate(["TagList"]));
  }
}

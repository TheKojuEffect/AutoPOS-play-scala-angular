import {Component} from "angular2/core";
import {RouteConfig} from "angular2/router";
import {AddTag} from "./add_tag";
import {ROUTER_DIRECTIVES} from "angular2/router";
import {TagList} from "./tag_list";
import {TagDetail} from "./tag_detail";
import {TagService} from "./tag_service";
import {EditTag} from "./edit_tag";

@Component({
  selector: "tags",
  templateUrl: "./app/catalog/tags/tags.html",
  directives: <any>[ROUTER_DIRECTIVES],
  providers: [TagService]
})

@RouteConfig(<any>[
  {path: "/", component: TagList, name: "TagList", useAsDefault: true},
  {path: "/add", component: AddTag, name: "AddTag"},
  {path: "/:id", component: TagDetail, name: "TagDetail"},
  {path: "/:id/edit", component: EditTag, name: "EditTag"}
])


export class Tags {
}

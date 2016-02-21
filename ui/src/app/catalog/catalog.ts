import {Component} from "angular2/core";
import {Items} from "./items/items";
import {Categories} from "./categories/categories";
import {Tags} from "./tags/tags";
import {Brands} from "./brands/brands";
import {RouteConfig} from "angular2/router";
import {Location} from "angular2/router";
import {ROUTER_DIRECTIVES} from "angular2/router";

@Component({
  selector: "catalog",
  templateUrl: "./app/catalog/catalog.html",
  directives: <any>[ROUTER_DIRECTIVES, Items, Categories, Brands, Tags]
})


@RouteConfig(<any>[
  {path: "/items/...", component: Items, name: "Items", useAsDefault: true},
  {path: "/categories/...", component: Categories, name: "Categories"},
  {path: "/brands/...", component: Brands, name: "Brands"}
  //{path: "/tags/...", component: Tags, name: "Tags"}
])


export class Catalog {

  constructor(private location:Location) {
  }

  public getPath() {
    return this.location.path();
  }

  public hasPath(route:string):boolean {
    return this.getPath().indexOf(route) !== -1;
  }

}

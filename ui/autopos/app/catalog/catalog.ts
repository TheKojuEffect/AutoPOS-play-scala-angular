import {Component} from "angular2/core";
import {UiTabs, UiPane} from "../../components/ui_tabs/ui_tabs";
import {Items} from "./items/items";
import {Categories} from "./categories/categories";
import {Tags} from "./tags/tags";
import {Brands} from "./brands/brands";
import {RouteConfig} from "angular2/router";

@Component({
  selector: "catalog",
  templateUrl: "./app/catalog/catalog.html",
  directives: <any>[UiTabs, UiPane, Items, Categories, Brands, Tags]
})

@RouteConfig(<any>[
  {path: "/items", component: Items, as: "Items", useAsDefault: true},
  {path: "/categories", component: Categories, as: "Categories"},
  {path: "/brands/...", component: Brands, as: "Brands"},
  {path: "/tags", component: Tags, as: "Tags"}
])

export class Catalog {

  constructor() {
    console.log("From Catalog");
  }
}

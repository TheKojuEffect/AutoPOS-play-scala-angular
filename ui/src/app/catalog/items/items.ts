import {Component} from "angular2/core";
import {RouteConfig} from "angular2/router";
import {AddItem} from "./add_item";
import {ROUTER_DIRECTIVES} from "angular2/router";
import {ItemList} from "./item_list";
import {ItemDetail} from "./item_detail";
import {ItemService} from "./item_service";
import {EditItem} from "./edit_item";
import {BrandService} from "../brands/brand_service";
import {CategoryService} from "../categories/category_service";

@Component({
  selector: "items",
  templateUrl: "./app/catalog/items/items.html",
  directives: <any>[ROUTER_DIRECTIVES],
  providers: [ItemService, BrandService, CategoryService]
})

@RouteConfig(<any>[
  {path: "/", component: ItemList, name: "ItemList", useAsDefault: true},
  {path: "/add", component: AddItem, name: "AddItem"},
  {path: "/:id", component: ItemDetail, name: "ItemDetail"},
  {path: "/:id/edit", component: EditItem, name: "EditItem"}
])


export class Items {
}

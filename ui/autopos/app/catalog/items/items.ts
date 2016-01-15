import {Component} from "angular2/core";
import {RouteConfig} from "angular2/router";
import {AddItem} from "./add_item";
import {ROUTER_DIRECTIVES} from "angular2/router";
import {ItemList} from "./item_list";
import {ItemDetail} from "./item_detail";
import {ItemService} from "./item_service";
import {EditItem} from "./edit_item";

@Component({
  selector: "items",
  templateUrl: "./app/catalog/items/items.html",
  directives: <any>[ROUTER_DIRECTIVES],
  providers: [ItemService]
})

@RouteConfig(<any>[
  {path: "/", component: ItemList, as: "ItemList", useAsDefault: true},
  {path: "/add", component: AddItem, as: "AddItem"},
  {path: "/:id", component: ItemDetail, as: "ItemDetail"},
  {path: "/:id/edit", component: EditItem, as: "EditItem"}
])


export class Items {
}

import {Component} from "angular2/core";
import {ItemService} from "./item_service";
import {Item} from "./item";
import {AddItem} from "./add_item";
import {ROUTER_DIRECTIVES} from "angular2/router";


@Component({
  selector: "item-list",
  templateUrl: "./app/catalog/items/item_list.html",
  directives: <any>[ROUTER_DIRECTIVES, AddItem]
})

export class ItemList {

  items:Array<Item>;

  constructor(private itemService:ItemService) {
    itemService.getItems()
      .subscribe(res => this.items = res.json());
  }

}

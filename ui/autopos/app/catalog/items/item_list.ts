import {Component} from "angular2/core";
import {ItemService} from "./item_service";
import {Item} from "./item";
import {AddItem} from "./add_item";
import {ROUTER_DIRECTIVES} from "angular2/router";
import {OnActivate} from "angular2/router";
import {ComponentInstruction} from "angular2/router";
import "rxjs/Rx";


@Component({
  selector: "item-list",
  templateUrl: "./app/catalog/items/item_list.html",
  directives: <any>[ROUTER_DIRECTIVES, AddItem]
})

export class ItemList implements OnActivate {


  items:Array<Item>;

  constructor(private itemService:ItemService) {
  }

  routerOnActivate(nextInstruction:ComponentInstruction, prevInstruction:ComponentInstruction):any {
    return this.itemService.getItems()
      .toPromise()
      .then(res => this.items = res.json());
  }

}

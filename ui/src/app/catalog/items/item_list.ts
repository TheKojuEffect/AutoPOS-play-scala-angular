import {Component} from "angular2/core";
import {ItemService} from "./item_service";
import {Item} from "./item";
import {AddItem} from "./add_item";
import {ROUTER_DIRECTIVES} from "angular2/router";
import {OnActivate} from "angular2/router";
import {ComponentInstruction} from "angular2/router";
import { PAGINATION_DIRECTIVES } from 'ng2-bootstrap/ng2-bootstrap';

import "rxjs/Rx";
import {PageMetadata} from "../../shared/PageMetadata";
import {Page} from "../../shared/Page";


@Component({
  selector: "item-list",
  templateUrl: "./app/catalog/items/item_list.html",
  directives: <any>[ROUTER_DIRECTIVES, PAGINATION_DIRECTIVES, AddItem]
})

export class ItemList implements OnActivate {

  items:Array<Item>;

  pageMetadata:PageMetadata;

  constructor(private itemService:ItemService) {
    this.pageMetadata = new PageMetadata();
    this.pageMetadata.page = 1;
    this.pageMetadata.size = 20;
  }

  routerOnActivate(nextInstruction:ComponentInstruction, prevInstruction:ComponentInstruction):any {
    return this.itemService.getItems()
      .toPromise()
      .then(res => {
          let page:Page<Item> = res.json();
          this.items = page.elements;
          this.pageMetadata = page.metadata;
        }
      );
  }

}

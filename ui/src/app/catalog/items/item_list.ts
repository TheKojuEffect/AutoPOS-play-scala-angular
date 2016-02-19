import {Component} from "angular2/core";
import {ItemService} from "./item_service";
import {Item} from "./item";
import {AddItem} from "./add_item";
import {ROUTER_DIRECTIVES} from "angular2/router";
import {OnActivate} from "angular2/router";
import {ComponentInstruction} from "angular2/router";
import { PAGINATION_DIRECTIVES } from 'ng2-bootstrap/ng2-bootstrap';

import "rxjs/Rx";


@Component({
  selector: "item-list",
  templateUrl: "./app/catalog/items/item_list.html",
  directives: <any>[ROUTER_DIRECTIVES, PAGINATION_DIRECTIVES, AddItem]
})

export class ItemList implements OnActivate {

  items:Array<Item>;

  private totalItems:number = 175;
  private currentPage:number = 1;
  private itemsPerPage:number = 20;

  constructor(private itemService:ItemService) {
  }

  routerOnActivate(nextInstruction:ComponentInstruction, prevInstruction:ComponentInstruction):any {
    return this.itemService.getItems()
      .toPromise()
      .then(res => this.items = res.json());
  }

  private setPage(pageNo:number):void {
    this.currentPage = pageNo;
  };

  private pageChanged(event:any):void {
    console.log('Page changed to: ' + event.page);
    console.log('Number items per page: ' + event.itemsPerPage);
  };

  private numOfPages(num:any):void {
    console.log("Number of pages: " + num);
  }


}

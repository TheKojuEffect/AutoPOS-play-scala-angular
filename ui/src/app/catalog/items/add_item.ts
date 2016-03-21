import {Component} from 'angular2/core';
import {ROUTER_DIRECTIVES, Router} from 'angular2/router';
import {ItemService} from './item_service';
import {BrandSelect} from '../brands/brand_select';
import {CategorySelect} from '../categories/category_select';
import {ItemCommand} from './item_command';

@Component({
  selector: "add-item",
  templateUrl: "./app/catalog/items/item_form.html",
  directives: [ROUTER_DIRECTIVES, BrandSelect, CategorySelect]
})

export class AddItem {

  title = "Add Item";

  item = new ItemCommand();

  constructor(private itemService:ItemService,
              private router:Router) {
  }

  onSubmit() {
    this.itemService.addItem(this.item)
      .subscribe(res => {
        this.router.navigate(["ItemDetail", {id: res.json().id}]);
      });
  }

}

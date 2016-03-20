import {Component} from 'angular2/core';
import {ROUTER_DIRECTIVES, Router} from 'angular2/router';
import {ItemService} from './item_service';
import {Item} from './item';
import {Brand} from '../brands/brand';
import {Category} from '../categories/category';
import {BrandSelect} from '../brands/brand_select';
import {CategorySelect} from '../categories/category_select';

@Component({
  selector: "add-item",
  templateUrl: "./app/catalog/items/item_form.html",
  directives: [ROUTER_DIRECTIVES, BrandSelect, CategorySelect]
})

export class AddItem {

  title = "Add Item";

  item = new Item();

  constructor(private itemService:ItemService,
              private router:Router) {
  }

  changeCategory(categoryId) {
    this.item.category = categoryId ? new Category(categoryId, "", "") : null;
  }

  changeBrand(brandId) {
    this.item.brand = brandId ? new Brand(brandId, "") : null;
  }

  onSubmit() {
    this.itemService.addItem(this.item)
      .subscribe(res => {
        this.router.navigate(["ItemDetail", {id: res.json().id}]);
      });
  }

}

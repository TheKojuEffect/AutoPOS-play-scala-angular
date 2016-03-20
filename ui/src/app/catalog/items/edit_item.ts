import {Component, OnInit} from 'angular2/core';
import {RouterLink, RouteParams, Router} from 'angular2/router';
import {ItemService} from './item_service';
import {Item} from './item';
import {Brand} from '../brands/brand';
import {Category} from '../categories/category';
import {BrandSelect} from '../brands/brand_select';
import {CategorySelect} from '../categories/category_select';

@Component({
  selector: "edit-item",
  templateUrl: "./app/catalog/items/item_form.html",
  directives: [RouterLink, BrandSelect, CategorySelect]
})

export class EditItem implements OnInit {

  title = "Edit Item";

  item:Item;

  constructor(private itemService:ItemService,
              private routeParams:RouteParams,
              private router:Router) {

  }

  ngOnInit() {
    let id = this.routeParams.get("id");
    this.itemService.getItem(parseInt(id))
      .subscribe(item => {
        this.item = item.json();
        this.title += " [" + this.item.code + "]";
      });
  }

  changeCategory(categoryId) {
    this.item.category = categoryId ? new Category(categoryId, "", "") : null;
  }

  changeBrand(brandId:number) {
    this.item.brand = brandId ? new Brand(brandId, "") : null;
  }

  onSubmit() {
    this.itemService.updateItem(this.item)
      .subscribe(() => {
          this.router.navigate(["ItemDetail", {id: this.item.id}]);
        }
      );
  }
}

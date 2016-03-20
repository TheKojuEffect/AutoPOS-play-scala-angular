import {Component, OnInit} from 'angular2/core';
import {ROUTER_DIRECTIVES, Router} from 'angular2/router';
import {ItemService} from './item_service';
import {Item} from './item';
import {CategoryService} from '../categories/category_service';
import {Brand} from '../brands/brand';
import {Category} from '../categories/category';
import {BrandSelect} from '../brands/brand_select';

@Component({
  selector: "add-item",
  templateUrl: "./app/catalog/items/item_form.html",
  directives: [ROUTER_DIRECTIVES, BrandSelect]
})

export class AddItem implements OnInit {

  title = "Add Item";

  item = new Item();

  categories:Category[];

  constructor(private itemService:ItemService,
              private categoryService:CategoryService,
              private router:Router) {
  }

  ngOnInit() {
    this.categoryService.getCategories()
      .subscribe(categories => this.categories = categories.json());

  }

  changeCategory(categoryId) {
    this.item.category = categoryId ? new Category(parseInt(categoryId), "", "") : null;
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

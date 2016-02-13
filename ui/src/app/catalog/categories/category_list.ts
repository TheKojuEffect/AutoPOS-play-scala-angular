import {Component} from "angular2/core";
import {CategoryService} from "./category_service";
import {Category} from "./category";
import {AddCategory} from "./add_category";
import {ROUTER_DIRECTIVES} from "angular2/router";


@Component({
  selector: "category-list",
  templateUrl: "./app/catalog/categories/category_list.html",
  directives: <any>[ROUTER_DIRECTIVES, AddCategory]
})

export class CategoryList {

  categories:Array<Category>;

  constructor(private categoryService:CategoryService) {
    categoryService.getCategories()
      .subscribe(res => this.categories = res.json());
  }

}

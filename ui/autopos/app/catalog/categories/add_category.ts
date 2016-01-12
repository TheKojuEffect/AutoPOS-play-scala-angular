import {Component} from "angular2/core";
import {ROUTER_DIRECTIVES} from "angular2/router";
import {CategoryService} from "./category_service";
import {Category} from "./category";
import {Router} from "angular2/router";

@Component({
  selector: "add-category",
  templateUrl: "./app/catalog/categories/category_form.html",
  directives: <any>[ROUTER_DIRECTIVES]
})

export class AddCategory {

  title = "Add Category";

  category = new Category(null, null, null);

  constructor(private categoryService:CategoryService,
              private router:Router) {
  }

  onSubmit() {
    this.categoryService.addCategory(this.category)
      .subscribe(res => {
        this.category = res.json();
        this.router.navigate(["CategoryDetail", {id: this.category.id}]);
      });
  }

}

import {Component} from "angular2/core";
import {RouterLink} from "angular2/router";
import {OnInit} from "angular2/core";
import {RouteParams} from "angular2/router";
import {CategoryService} from "./category_service";
import {Category} from "./category";
import {Router} from "angular2/router";

@Component({
  selector: "edit-category",
  templateUrl: "./app/catalog/categories/category_form.html",
  directives: <any>[RouterLink]
})

export class EditCategory implements OnInit {

  title = "Edit Category";

  category:Category;

  constructor(private categoryService:CategoryService,
              private routeParams:RouteParams,
              private router:Router) {

  }

  ngOnInit() {
    let id = this.routeParams.get("id");
    this.categoryService.getCategory(parseInt(id))
      .subscribe(category => {
        this.category = category.json();
        this.title += " [" + this.category.shortName + "]";
      });
  }

  onSubmit() {
    this.categoryService.updateCategory(this.category)
      .subscribe(()  => {
          this.router.navigate(["CategoryDetail", {id: this.category.id}]);
        }
      );
  }
}

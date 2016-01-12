import {Component} from "angular2/core";
import {CategoryService} from "./category_service";
import {Router} from "angular2/router";
import {RouteParams} from "angular2/router";
import {OnInit} from "angular2/core";
import {Category} from "./category";
import {RouterLink} from "angular2/router";
import {Modal} from "angular2-modal/dist/angular2-modal";
import {YesNoModal} from "angular2-modal/dist/angular2-modal";
import {Injector} from "angular2/core";
import {YesNoModalContent} from "angular2-modal/dist/angular2-modal";
import {ICustomModal} from "angular2-modal/dist/angular2-modal";
import {provide} from "angular2/core";

@Component({
  selector: "category-detail",
  templateUrl: "./app/catalog/categories/category_detail.html",
  directives: <any>[RouterLink],
  providers: [Modal]
})

export class CategoryDetail implements OnInit {

  category:Category;

  constructor(private categoryService:CategoryService,
              private routeParams:RouteParams,
              private router:Router,
              private modal:Modal) {
  }


  ngOnInit() {
    let id = this.routeParams.get("id");
    this.categoryService.getCategory(parseInt(id))
      .subscribe(category => this.category = category.json());
  }

  deleteCategory() {
    let bindings = Injector.resolve([
      provide(ICustomModal,
        {useValue: new YesNoModalContent("Delete Category?", "Are you sure you want to delete this category?", false, "Yes", "No")})
    ]);

    this.modal.open(<any>YesNoModal, bindings)
      .then(r => r.result.then(re => {
        if (re) {
          this.confirmDeleteCategory();
        }
      }));
  }

  private confirmDeleteCategory() {
    this.categoryService.deleteCategory(this.category.id)
      .subscribe(() => this.router.navigate(["CategoryList"]));
  }
}

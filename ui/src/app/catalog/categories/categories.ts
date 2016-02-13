import {Component} from "angular2/core";
import {RouteConfig} from "angular2/router";
import {AddCategory} from "./add_category";
import {ROUTER_DIRECTIVES} from "angular2/router";
import {CategoryList} from "./category_list";
import {CategoryDetail} from "./category_detail";
import {CategoryService} from "./category_service";
import {EditCategory} from "./edit_category";

@Component({
  selector: "categories",
  templateUrl: "./app/catalog/categories/categories.html",
  directives: <any>[ROUTER_DIRECTIVES],
  providers: [CategoryService]
})

@RouteConfig(<any>[
  {path: "/", component: CategoryList, as: "CategoryList", useAsDefault: true},
  {path: "/add", component: AddCategory, as: "AddCategory"},
  {path: "/:id", component: CategoryDetail, as: "CategoryDetail"},
  {path: "/:id/edit", component: EditCategory, as: "EditCategory"}
])


export class Categories {
}

import {Component} from "angular2/core";
import {RouteConfig} from "angular2/router";
import {ROUTER_PROVIDERS} from "angular2/router";
import {RouterOutlet} from "angular2/router";
import {AddBrand} from "./add_brand";
import {ROUTER_DIRECTIVES} from "angular2/router";
import {BrandList} from "./brand_list";

@Component({
  selector: "brands",
  templateUrl: "./app/catalog/brands/brands.html",
  directives: <any>[ROUTER_DIRECTIVES]
})

@RouteConfig(<any>[
  {path: "/", component: BrandList, as: "BrandList", useAsDefault: true},
  {path: "/add", component: AddBrand, as: "AddBrand"}
])

export class Brands {
}

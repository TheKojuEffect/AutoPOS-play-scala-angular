import {Component} from "angular2/core";
import {BrandListCmp} from "./brand-list-cmp";
import {RouteConfig} from "angular2/router";
import {ROUTER_PROVIDERS} from "angular2/router";
import {RouterOutlet} from "angular2/router";
import {AddBrand} from "./add_brand";
import {ROUTER_DIRECTIVES} from "angular2/router";

@Component({
  selector: "brands",
  templateUrl: "./components/brand/brands.html",
  directives: <any>[ROUTER_DIRECTIVES]
})

@RouteConfig(<any>[
  {path: "/", component: BrandListCmp, as: "BrandList", useAsDefault: true},
  {path: "/add", component: AddBrand, as: "AddBrand"}
])

export class BrandCmp {
}

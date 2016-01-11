import {Component} from "angular2/core";
import {RouteConfig} from "angular2/router";
import {AddBrand} from "./add_brand";
import {ROUTER_DIRECTIVES} from "angular2/router";
import {BrandList} from "./brand_list";
import {BrandDetail} from "./brand_detail";
import {BrandService} from "./brand_service";

@Component({
  selector: "brands",
  templateUrl: "./app/catalog/brands/brands.html",
  directives: <any>[ROUTER_DIRECTIVES],
  providers: [BrandService]
})

@RouteConfig(<any>[
  {path: "/", component: BrandList, as: "BrandList", useAsDefault: true},
  {path: "/add", component: AddBrand, as: "AddBrand"},
  {path: "/:id", component: BrandDetail, as: "BrandDetail"}
])


export class Brands {
}

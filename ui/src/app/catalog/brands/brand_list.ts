import {Component} from "angular2/core";
import {BrandService} from "./brand_service";
import {Brand} from "./brand";
import {AddBrand} from "./add_brand";
import {ROUTER_DIRECTIVES} from "angular2/router";


@Component({
  selector: "brand-list",
  templateUrl: "./app/catalog/brands/brand_list.html",
  directives: <any>[ROUTER_DIRECTIVES, AddBrand]
})

export class BrandList {

  brands:Array<Brand>;

  constructor(private brandService:BrandService) {
    brandService.getBrands()
      .subscribe(res => this.brands = res.json());
  }

}

import {Component} from 'angular2/core';
import {BrandService} from "./brand-service";
import {Brand} from "./brand";
import {HTTP_PROVIDERS} from "angular2/http";
import {Http, Response} from "angular2/http";
import {AddBrand} from "./add_brand";
import {ROUTER_DIRECTIVES} from "angular2/router";


@Component({
  selector: 'brand-list',
  templateUrl: './components/brand/brand-list.html',
  //viewProviders: [HTTP_PROVIDERS],
  providers: [BrandService],
  directives: <any>[ROUTER_DIRECTIVES, AddBrand]
})

export class BrandListCmp {

  brands:Array<Brand>;

  constructor(private brandService:BrandService) {
    brandService.getBrands()
      .subscribe(res => this.brands = res.json());
  }

}

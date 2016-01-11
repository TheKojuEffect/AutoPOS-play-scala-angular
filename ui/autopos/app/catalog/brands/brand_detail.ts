import {Component} from "angular2/core";
import {BrandService} from "./brand_service";
import {Router} from "angular2/router";
import {RouteParams} from "angular2/router";
import {OnInit} from "angular2/core";
import {Brand} from "./brand";
import {RouterLink} from "angular2/router";

@Component({
  selector: "brand-detail",
  templateUrl: "./app/catalog/brands/brand_detail.html",
  directives: [RouterLink]
})

export class BrandDetail {

  brand:Brand;

  constructor(private brandService:BrandService,
              private routeParams:RouteParams) {
  }


  ngOnInit() {
    let id = this.routeParams.get("id");
    this.brandService.getBrand(parseInt(id))
      .subscribe(brand => this.brand = brand.json());
  }

}

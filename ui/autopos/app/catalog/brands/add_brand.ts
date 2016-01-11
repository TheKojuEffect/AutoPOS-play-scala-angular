import {Component} from "angular2/core";
import {ROUTER_DIRECTIVES} from "angular2/router";
import {BrandService} from "./brand_service";
import {NgForm}    from 'angular2/common';
import {Brand} from "./brand";
import {Router} from "angular2/router";

@Component({
  selector: "add-brand",
  templateUrl: "./app/catalog/brands/brand_form.html",
  directives: <any>[ROUTER_DIRECTIVES]
})

export class AddBrand {

  title = "Add Brand";

  brand = new Brand();

  constructor(private brandService:BrandService,
              private router:Router) {
  }

  onSubmit() {
    this.brandService.addBrand(this.brand)
      .subscribe(res => {
        this.brand = res.json();
        this.router.navigate(['BrandDetail', {id: this.brand.id}]);
      });
  }

}

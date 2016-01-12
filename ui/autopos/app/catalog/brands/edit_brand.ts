import {Component} from "angular2/core";
import {RouterLink} from "angular2/router";
import {OnInit} from "angular2/core";
import {RouteParams} from "angular2/router";
import {BrandService} from "./brand_service";
import {Brand} from "./brand";
import {Router} from "angular2/router";

@Component({
  selector: "edit-brand",
  templateUrl: "./app/catalog/brands/brand_form.html",
  directives: <any>[RouterLink]
})

export class EditBrand implements OnInit {

  title = "Edit Brand";

  brand:Brand;

  constructor(private brandService:BrandService,
              private routeParams:RouteParams,
              private router:Router) {

  }

  ngOnInit() {
    let id = this.routeParams.get("id");
    this.brandService.getBrand(parseInt(id))
      .subscribe(brand => {
        this.brand = brand.json();
        this.title += " [" + this.brand.name + "]";
      });
  }

  onSubmit() {
    this.brandService.updateBrand(this.brand)
      .subscribe(()  => {
          this.router.navigate(["BrandDetail", {id: this.brand.id}]);
        }
      );
  }
}

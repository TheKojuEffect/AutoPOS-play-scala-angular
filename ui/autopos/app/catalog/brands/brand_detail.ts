import {Component} from "angular2/core";
import {BrandService} from "./brand_service";
import {Router} from "angular2/router";
import {RouteParams} from "angular2/router";
import {OnInit} from "angular2/core";
import {Brand} from "./brand";
import {RouterLink} from "angular2/router";
import {Modal} from "angular2-modal/dist/angular2-modal";
import {YesNoModal} from "angular2-modal/dist/angular2-modal";
import {Injector} from "angular2/core";
import {YesNoModalContent} from "angular2-modal/dist/angular2-modal";
import {ICustomModal} from "angular2-modal/dist/angular2-modal";
import {provide} from "angular2/core";
import {IterableDiffers} from "angular2/core";
import {KeyValueDiffers} from "angular2/core";
import {Renderer} from "angular2/core"
import {ModalDialogInstance} from "angular2-modal/dist/angular2-modal";
import {ModalConfig} from "angular2-modal/dist/angular2-modal";

@Component({
  selector: "brand-detail",
  templateUrl: "./app/catalog/brands/brand_detail.html",
  directives: <any>[RouterLink],
  providers: [Modal]
})

export class BrandDetail {

  brand:Brand;

  constructor(private brandService:BrandService,
              private routeParams:RouteParams,
              private router:Router,
              private modal:Modal) {
  }


  ngOnInit() {
    let id = this.routeParams.get("id");
    this.brandService.getBrand(parseInt(id))
      .subscribe(brand => this.brand = brand.json());
  }

  deleteBrand() {
    let bindings = Injector.resolve([
      provide(ICustomModal,
        {useValue: new YesNoModalContent("Delete Brand?", "Are you sure you want to delete this brand?", false, "Yes", "No")})
    ]);

    this.modal.open(<any>YesNoModal, bindings)
      .then(r => r.result.then(re => {
        if (re) {
          this.confirmDeleteBrand()
        }
      }));
  }

  private confirmDeleteBrand() {
    this.brandService.deleteBrand(this.brand.id)
      .subscribe(() => this.router.navigate(['BrandList']));
  }
}

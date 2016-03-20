import {OnInit, Component, EventEmitter, Input, Output} from 'angular2/core';
import {BrandService} from './brand_service';
import {Brand} from './brand';
import {Observable} from 'rxjs/Observable';

@Component({
  selector: 'brand-select',
  templateUrl: './app/catalog/brands/brand_select.html',
  providers: [BrandService]
})
export class BrandSelect implements OnInit {

  brands:Observable<Brand[]>;

  @Input()
  brandId:number;

  @Output()
  select = new EventEmitter<number>();

  constructor(private brandService:BrandService) {
  }

  ngOnInit() {
    this.brands = this.brandService.getBrands()
      .map(res => res.json());
  }

  changeBrand(brandId) {
    this.select.emit(+brandId);
  }

}

import {Component, OnInit} from 'angular2/core';
import {RouterLink, RouteParams, Router} from 'angular2/router';
import {ItemService} from './item_service';
import {Item} from './item';
import {CategoryService} from '../categories/category_service';
import {BrandService} from '../brands/brand_service';
import {Brand} from '../brands/brand';
import {Category} from '../categories/category';
import {BrandSelect} from '../brands/brand_select';

@Component({
  selector: "edit-item",
  templateUrl: "./app/catalog/items/item_form.html",
  directives: [RouterLink, BrandSelect]
})

export class EditItem implements OnInit {

  title = "Edit Item";

  item:Item;

  categories:Category[];
  brands:Brand[];

  constructor(private itemService:ItemService,
              private categoryService:CategoryService,
              private brandService:BrandService,
              private routeParams:RouteParams,
              private router:Router) {

  }

  ngOnInit() {
    let id = this.routeParams.get("id");
    this.itemService.getItem(parseInt(id))
      .subscribe(item => {
        this.item = item.json();
        this.title += " [" + this.item.code + "]";
      });

    this.brandService.getBrands()
      .subscribe(brands => this.brands = brands.json());

    this.categoryService.getCategories()
      .subscribe(categories => this.categories = categories.json());
  }

  changeCategory(categoryId) {
    this.item.category = categoryId ? new Category(parseInt(categoryId), "", "") : null;
  }

  changeBrand(brandId:number) {
    this.item.brand = brandId ? new Brand(brandId, "") : null;
  }

  onSubmit() {
    this.itemService.updateItem(this.item)
      .subscribe(() => {
          this.router.navigate(["ItemDetail", {id: this.item.id}]);
        }
      );
  }
}

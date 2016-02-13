import {Component} from "angular2/core";
import {ROUTER_DIRECTIVES} from "angular2/router";
import {ItemService} from "./item_service";
import {Item} from "./item";
import {Router} from "angular2/router";
import {BrandService} from "../brands/brand_service";
import {CategoryService} from "../categories/category_service";
import {OnInit} from "angular2/core";
import {Brand} from "../brands/brand";
import {Category} from "../categories/category";

@Component({
  selector: "add-item",
  templateUrl: "./app/catalog/items/item_form.html",
  directives: <any>[ROUTER_DIRECTIVES]
})

export class AddItem implements OnInit {

  title = "Add Item";

  item = new Item();

  brands:Brand[];
  categories:Category[];

  constructor(private itemService:ItemService,
              private brandService:BrandService,
              private categoryService:CategoryService,
              private router:Router) {
  }

  ngOnInit() {
    this.brandService.getBrands()
      .subscribe(brands => this.brands = brands.json());

    this.categoryService.getCategories()
      .subscribe(categories => this.categories = categories.json());

  }

  changeCategory(categoryId) {
    this.item.category = categoryId ? new Category(parseInt(categoryId), "", "") : null;
  }

  changeBrand(brandId) {
    this.item.brand = brandId ? new Brand(parseInt(brandId), "") : null;
  }

  onSubmit() {
    this.itemService.addItem(this.item)
      .subscribe(res => {
        this.router.navigate(["ItemDetail", {id: res.json().id}]);
      });
  }

}

import {OnInit, Component, EventEmitter, Input, Output} from 'angular2/core';
import {CategoryService} from './category_service';
import {Category} from './category';
import {Observable} from 'rxjs/Observable';

@Component({
  selector: 'category-select',
  templateUrl: './app/catalog/categories/category_select.html',
  providers: [CategoryService]
})
export class CategorySelect implements OnInit {

  categories:Observable<Category[]>;

  @Input()
  categoryId:number;

  @Output()
  select = new EventEmitter<number>();

  constructor(private categoryService:CategoryService) {
  }

  ngOnInit() {
    this.categories = this.categoryService.getCategories()
      .map(res => res.json());
  }

  changeCategory(categoryId) {
    this.select.emit(+categoryId || null);
  }

}

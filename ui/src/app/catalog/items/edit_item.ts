import {Component, OnInit} from 'angular2/core';
import {RouterLink, RouteParams, Router} from 'angular2/router';
import {ItemService} from './item_service';
import {BrandSelect} from '../brands/brand_select';
import {CategorySelect} from '../categories/category_select';
import {ItemCommand} from './item_command';
import {Item} from './item';

@Component({
  selector: "edit-item",
  templateUrl: "./app/catalog/items/item_form.html",
  directives: [RouterLink, BrandSelect, CategorySelect]
})

export class EditItem implements OnInit {

  title = "Edit Item";

  item:ItemCommand;

  constructor(private itemService:ItemService,
              private routeParams:RouteParams,
              private router:Router) {
  }

  ngOnInit() {
    let id = this.routeParams.get("id");
    this.itemService.getItem(parseInt(id))
      .subscribe(item => {
        const i:Item = item.json();
        this.item = ItemCommand.fromItem(i);
        this.title += " [" + i.code + "]";
      });
  }

  onSubmit() {
    this.itemService.updateItem(this.item)
      .subscribe(() => {
          this.router.navigate(["ItemDetail", {id: this.item.id}]);
        }
      );
  }
}

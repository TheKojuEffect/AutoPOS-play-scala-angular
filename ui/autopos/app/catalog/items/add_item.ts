import {Component} from "angular2/core";
import {ROUTER_DIRECTIVES} from "angular2/router";
import {ItemService} from "./item_service";
import {Item} from "./item";
import {Router} from "angular2/router";

@Component({
  selector: "add-item",
  templateUrl: "./app/catalog/items/item_form.html",
  directives: <any>[ROUTER_DIRECTIVES]
})

export class AddItem {

  title = "Add Item";

  item = new Item(null, null, null, null);

  constructor(private itemService:ItemService,
              private router:Router) {
  }

  onSubmit() {
    this.itemService.addItem(this.item)
      .subscribe(res => {
        this.item = res.json();
        this.router.navigate(["ItemDetail", {id: this.item.id}]);
      });
  }

}

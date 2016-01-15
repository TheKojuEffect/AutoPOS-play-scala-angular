import {Component} from "angular2/core";
import {RouterLink} from "angular2/router";
import {OnInit} from "angular2/core";
import {RouteParams} from "angular2/router";
import {ItemService} from "./item_service";
import {Item} from "./item";
import {Router} from "angular2/router";

@Component({
  selector: "edit-item",
  templateUrl: "./app/catalog/items/item_form.html",
  directives: <any>[RouterLink]
})

export class EditItem implements OnInit {

  title = "Edit Item";

  item:Item;

  constructor(private itemService:ItemService,
              private routeParams:RouteParams,
              private router:Router) {

  }

  ngOnInit() {
    let id = this.routeParams.get("id");
    this.itemService.getItem(parseInt(id))
      .subscribe(item => {
        this.item = item.json();
        this.title += " [" + this.item.name + "]";
      });
  }

  onSubmit() {
    this.itemService.updateItem(this.item)
      .subscribe(()  => {
          this.router.navigate(["ItemDetail", {id: this.item.id}]);
        }
      );
  }
}

import {Component} from "angular2/core";
import {ItemService} from "./item_service";
import {Router} from "angular2/router";
import {RouteParams} from "angular2/router";
import {OnInit} from "angular2/core";
import {Item} from "./item";
import {RouterLink} from "angular2/router";
import {Modal} from "angular2-modal/dist/angular2-modal";
import {YesNoModal} from "angular2-modal/dist/angular2-modal";
import {Injector} from "angular2/core";
import {YesNoModalContent} from "angular2-modal/dist/angular2-modal";
import {ICustomModal} from "angular2-modal/dist/angular2-modal";
import {provide} from "angular2/core";

@Component({
  selector: "item-detail",
  templateUrl: "./app/catalog/items/item_detail.html",
  directives: <any>[RouterLink],
  providers: [Modal]
})

export class ItemDetail implements OnInit {

  item:Item;

  constructor(private itemService:ItemService,
              private routeParams:RouteParams,
              private router:Router,
              private modal:Modal) {
  }


  ngOnInit() {
    let id = this.routeParams.get("id");
    this.itemService.getItem(parseInt(id))
      .subscribe(item => this.item = item.json());
  }

  deleteItem() {
    let bindings = Injector.resolve([
      provide(ICustomModal,
        {useValue: new YesNoModalContent("Delete Item?", "Are you sure you want to delete this item?", false, "Yes", "No")})
    ]);

    this.modal.open(<any>YesNoModal, bindings)
      .then(r => r.result.then(re => {
        if (re) {
          this.confirmDeleteItem();
        }
      }));
  }

  private confirmDeleteItem() {
    this.itemService.deleteItem(this.item.id)
      .subscribe(() => this.router.navigate(["ItemList"]));
  }
}

import {Component} from "angular2/core";
import {
  RouteConfig,
  ROUTER_DIRECTIVES
} from "angular2/router";

import {Navigation} from "./navigation/navigation";
import {Catalog} from "./catalog/catalog";
import {Home} from "./home/home";


@Component({
  selector: "autopos",
  templateUrl: "./app/autopos.html",
  directives: <any>[Navigation, ROUTER_DIRECTIVES]
})


@RouteConfig(<any>[
  {path: "/", component: Home, name: "Home", useAsDefault: true},
  {path: "/catalog/...", component: Catalog, name: "Catalog"}
])


export class Autopos {
}

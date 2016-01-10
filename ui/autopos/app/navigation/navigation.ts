import {Component} from "angular2/core";
import {Location, ROUTER_DIRECTIVES} from "angular2/router";

@Component({
  selector: "navigation",
  templateUrl: "./app/navigation/navigation.html",
  directives: <any>[ROUTER_DIRECTIVES]
})

export class Navigation {

  constructor(private _location:Location) {
  }

  public getPath() {
    return this._location.path();
  }

  public hasPath(route:string):boolean {
    return this.getPath().indexOf(route) !== -1;
  }
}

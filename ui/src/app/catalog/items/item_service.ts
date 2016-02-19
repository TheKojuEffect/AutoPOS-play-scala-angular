import {Http} from "angular2/http";
import {Injectable} from "angular2/core";
import {Item} from "./item";

@Injectable()
export class ItemService {

  private baseUrl = "/items";

  constructor(private http:Http) {
  }

  public getItems() {
    return this.http.get(this.baseUrl);
  }

  public getItem(id:number) {
    let url = this.urlWithId(id);
    return this.http.get(url);
  }

  public addItem(item:Item) {
    return this.http.post(this.baseUrl, JSON.stringify(item));
  }

  public updateItem(item:Item) {
    let url = this.urlWithId(item.id);
    return this.http.put(url, JSON.stringify(item));
  }

  public deleteItem(id:number) {
    let url = this.urlWithId(id);
    return this.http.delete(url);
  }

  private urlWithId(id:number) {
    return this.baseUrl + "/" + id;
  }
}


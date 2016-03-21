import {Http} from 'angular2/http';
import {Injectable} from 'angular2/core';
import {Pageable} from '../../shared/Pageable';
import {ItemCommand} from './item_command';

@Injectable()
export class ItemService {

  private baseUrl = "http://localhost:9000/api/items";

  constructor(private http:Http) {
  }

  public getItems(pageable:Pageable) {
    let url = this.baseUrl + "?" + pageable.queryString();
    return this.http.get(url);
  }

  public getItem(id:number) {
    let url = this.urlWithId(id);
    return this.http.get(url);
  }

  public addItem(itemCommand:ItemCommand) {
    return this.http.post(this.baseUrl, JSON.stringify(itemCommand));
  }

  public updateItem(itemCommand:ItemCommand) {
    let url = this.urlWithId(itemCommand.id);
    return this.http.put(url, JSON.stringify(itemCommand));
  }

  public deleteItem(id:number) {
    let url = this.urlWithId(id);
    return this.http.delete(url);
  }

  private urlWithId(id:number) {
    return this.baseUrl + "/" + id;
  }
}


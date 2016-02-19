import {Http} from "angular2/http";
import {Injectable} from "angular2/core";
import {Category} from "./category";

@Injectable()
export class CategoryService {

  private baseUrl = "/categories";

  constructor(private http:Http) {
  }

  public getCategories() {
    return this.http.get(this.baseUrl);
  }

  public getCategory(id:number) {
    let url = this.urlWithId(id);
    return this.http.get(url);
  }

  public addCategory(category:Category) {
    return this.http.post(this.baseUrl, JSON.stringify(category));
  }

  public updateCategory(category:Category) {
    let url = this.urlWithId(category.id);
    return this.http.put(url, JSON.stringify(category));
  }

  public deleteCategory(id:number) {
    let url = this.urlWithId(id);
    return this.http.delete(url);
  }

  private urlWithId(id:number) {
    return this.baseUrl + "/" + id;
  }
}


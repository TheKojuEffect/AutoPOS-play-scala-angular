import {Http} from "angular2/http";
import {Injectable} from "angular2/core";
import {Tag} from "./tag";

@Injectable()
export class TagService {

  private baseUrl = "http://localhost:9000/api/tags";

  constructor(private http:Http) {
  }

  public getTags() {
    return this.http.get(this.baseUrl);
  }

  public getTag(id:number) {
    let url = this.urlWithId(id);
    return this.http.get(url);
  }

  public addTag(tag:Tag) {
    return this.http.post(this.baseUrl, JSON.stringify(tag));
  }

  public updateTag(tag:Tag) {
    let url = this.urlWithId(tag.id);
    return this.http.put(url, JSON.stringify(tag));
  }

  public deleteTag(id:number) {
    let url = this.urlWithId(id);
    return this.http.delete(url);
  }

  private urlWithId(id:number) {
    return this.baseUrl + "/" + id;
  }
}


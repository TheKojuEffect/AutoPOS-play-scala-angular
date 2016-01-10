import {Http} from "angular2/http";
import {Injectable} from "angular2/core";

@Injectable()
export class BrandService {

  constructor(private http:Http) {
  }

  public getBrands() {
    return this.http.request("http://localhost:9000/api/brands");
  }
}


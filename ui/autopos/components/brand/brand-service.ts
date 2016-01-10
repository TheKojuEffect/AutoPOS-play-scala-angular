import {Http, Response} from "angular2/http";
import {Injectable} from "angular2/core";
import {Brand} from "./brand";

@Injectable()
export class BrandService {

  constructor(private http:Http) {
  }

  public getBrands() {
    return this.http.request("http://localhost:9000/api/brands");
  }
}


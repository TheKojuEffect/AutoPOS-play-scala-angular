import {Http} from "angular2/http";
import {Injectable} from "angular2/core";
import {Brand} from "./brand";
import {Headers} from "angular2/http";

@Injectable()
export class BrandService {

  private baseUrl = "http://localhost:9000/api/brands";

  constructor(private http:Http) {
  }

  public getBrands() {
    return this.http.get(this.baseUrl);
  }

  public addBrand(brand:Brand) {
    return this.http.post(this.baseUrl, JSON.stringify(brand));
  }

  public getBrand(id:number) {
    return this.http.get(this.baseUrl + "/" + id)
  }

}


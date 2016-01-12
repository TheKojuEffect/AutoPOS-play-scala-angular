import {Http} from "angular2/http";
import {Injectable} from "angular2/core";
import {Brand} from "./brand";
import {Headers} from "angular2/http";

@Injectable()
export class BrandService {

  private baseUrl = "http://localhost:9000/api/brands";

  constructor(private http:Http) {
  }

  private urlWithId(id:number) {
    return this.baseUrl + "/" + id;
  }

  public getBrands() {
    return this.http.get(this.baseUrl);
  }

  public getBrand(id:number) {
    let url = this.urlWithId(id);
    return this.http.get(url);
  }

  public addBrand(brand:Brand) {
    return this.http.post(this.baseUrl, JSON.stringify(brand));
  }

  public updateBrand(brand:Brand) {
    let url = this.urlWithId(brand.id);
    return this.http.put(url, JSON.stringify(brand));
  }

  public deleteBrand(id:number) {
    let url = this.urlWithId(id);
    return this.http.delete(url);
  }

}


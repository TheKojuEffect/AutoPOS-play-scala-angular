import {Category} from "../categories/category";
import {Brand} from "../brands/brand";
export class Item {

  public id:number;
  public code:string;
  public name:string;
  public markedPrice:number;
  public description:string;
  public location:string;
  public remarks:string;
  public category:Category;
  public brand:Brand;

}

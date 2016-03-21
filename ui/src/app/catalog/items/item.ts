import {Category} from '../categories/category';
import {Brand} from '../brands/brand';

export class Item {

  id:number;
  code:string;
  name:string;
  markedPrice:number;
  quantity:number;
  description:string;
  location:string;
  remarks:string;
  category:Category;
  brand:Brand;

}

import {Item} from './item';
export class ItemCommand {
  id:number;
  name:string;
  markedPrice:number;
  quantity:number;
  description:string;
  location:string;
  remarks:string;
  categoryId:number;
  brandId:number;


  static fromItem(item:Item):ItemCommand {
    const c = new ItemCommand();
    c.id = item.id;
    c.name = item.name;
    c.markedPrice = item.markedPrice;
    c.quantity = item.quantity;
    c.description = item.description;
    c.location = item.location;
    c.remarks = item.remarks;
    c.categoryId = item.category && item.category.id;
    c.brandId = item.brand && item.brand.id;
    return c;
  }
}

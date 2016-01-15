export class Item {

  constructor(public id:number,
              public code:string,
              public name:string,
              public markedPrice:number,
              public description?:string,
              public remarks?:string,
              public categoryId?:number,
              public brandId?:number) {
  }
}

import {Pageable} from "./Pageable";

export class Pager implements Pageable {

  constructor(private page:number, private size:number) {
  }


  queryString():string {
    return `page=${this.page}&size=${this.size}`;
  }

}

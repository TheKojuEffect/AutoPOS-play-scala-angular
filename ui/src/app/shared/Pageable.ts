export interface Pageable {
  page: number;
  size: number;

  queryString():string;
}

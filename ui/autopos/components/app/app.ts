import {Component} from 'angular2/core';
import {
  RouteConfig,
  ROUTER_DIRECTIVES
} from 'angular2/router';
// import {HTTP_PROVIDERS} from 'angular2/http';

import {HomeCmp} from '../home/home-cmp';
import {NavigationCmp} from '../navigation/navigation-cmp';
import {ItemListCmp} from '../item/item-list-cmp';


@Component({
  selector: 'autopos',
  templateUrl: './components/app/app.html',
  directives: [ItemListCmp, NavigationCmp, ROUTER_DIRECTIVES]
})


@RouteConfig([
  {path: '/', component: HomeCmp, as: 'Home'},
  {path: '/items', component: ItemListCmp, as: 'ItemList'}
])


export class AppCmp {
}

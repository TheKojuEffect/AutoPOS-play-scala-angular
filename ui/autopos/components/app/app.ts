import {Component} from 'angular2/core';
import {
  RouteConfig,
  ROUTER_DIRECTIVES
} from 'angular2/router';
// import {HTTP_PROVIDERS} from 'angular2/http';

import {HomeCmp} from '../home/home-cmp';
import {NavigationCmp} from '../navigation/navigation-cmp';
import {ItemCmp} from '../item/item-cmp';


@Component({
  selector: 'autopos',
  templateUrl: './components/app/app.html',
  directives: [NavigationCmp, ROUTER_DIRECTIVES]
})


@RouteConfig([
  {path: '/', component: HomeCmp, as: 'Home'},
  {path: '/items', component: ItemCmp, as: 'Item'}
])


export class AppCmp {
}

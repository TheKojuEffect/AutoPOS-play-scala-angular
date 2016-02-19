import {provide} from "angular2/core";
import {bootstrap} from "angular2/platform/browser";
import {ROUTER_PROVIDERS, APP_BASE_HREF, LocationStrategy, HashLocationStrategy} from "angular2/router";
import {HTTP_PROVIDERS} from "angular2/http";
import {Autopos} from "./app/autopos";
import {RequestOptions, BaseRequestOptions} from "angular2/http";
import {Headers} from "angular2/http";

class HttpOptions extends BaseRequestOptions {
  constructor() {
    super();
    var headers = new Headers();
    headers.append("Content-Type", "application/json");
    this.headers = headers;
  }
}

//noinspection TypeScriptValidateTypes
bootstrap(Autopos, [
  provide(APP_BASE_HREF, {useValue: '/'}),
  ROUTER_PROVIDERS,
  HTTP_PROVIDERS,
  provide(LocationStrategy, {useClass: HashLocationStrategy}),
  provide(RequestOptions, {useClass: HttpOptions})
]);

// In order to start the Service Worker located at "./sw.js"
// uncomment this line. More about Service Workers here
// https://developer.mozilla.org/en-US/docs/Web/API/Service_Worker_API/Using_Service_Workers
// if ('serviceWorker' in navigator) {
//   (<any>navigator).serviceWorker.register('./sw.js').then(function(registration) {
//     console.log('ServiceWorker registration successful with scope: ',    registration.scope);
//   }).catch(function(err) {
//     console.log('ServiceWorker registration failed: ', err);
//   });
// }

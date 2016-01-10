import {provide} from "angular2/core";
import {bootstrap} from "angular2/platform/browser";
import {ROUTER_PROVIDERS, APP_BASE_HREF, LocationStrategy, HashLocationStrategy} from "angular2/router";
import {HTTP_PROVIDERS} from "angular2/http";
import {Autopos} from "./app/autopos";

//noinspection TypeScriptValidateTypes
bootstrap(Autopos, [
  provide(APP_BASE_HREF, {useValue: "<%= APP_ROOT %>"}),
  ROUTER_PROVIDERS,
  HTTP_PROVIDERS,
  provide(LocationStrategy, {useClass: HashLocationStrategy})
]);

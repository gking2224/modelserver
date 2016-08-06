"use strict";

/// <reference path="../../typings/index.d.ts" />

// import the application
import {AppComponent} from "./app";
import { modelserverRouterProviders } from './modelserver-console.routes';
import {environment} from '../core/commons/environment';
// import Angular 2
import { bootstrap } from '@angular/platform-browser-dynamic';
import {APP_BASE_HREF} from '@angular/common';
import { enableProdMode } from '@angular/core';
import { HTTP_PROVIDERS } from '@angular/http';

// import Angular 2 Component Router
// reference: http://blog.thoughtram.io/angular/2015/06/16/routing-in-angular-2.html
import {LocationStrategy, PathLocationStrategy, HashLocationStrategy} from "@angular/common";
import {appServicesInjectables} from "./services/services";
import {LoggingService} from "./services/logging.service";

if (environment.production) {
  enableProdMode();
}

// bootstrap our app
console.log("Bootstrapping the AppComponent");

bootstrap(AppComponent, [
    appServicesInjectables// alternative way of filling the injector with all the classes we want to be able to inject
  , LoggingService
  , modelserverRouterProviders
  , {provide: APP_BASE_HREF, useValue: '/' }
  , HTTP_PROVIDERS,
    // ELEMENT_PROBE_PROVIDERS, // remove in production
    { provide : LocationStrategy, useClass: HashLocationStrategy }
]).then(
  (success: any) => console.log("Bootstrap successful"),
  (error: any) => console.error(error)
);


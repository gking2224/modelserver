"use strict";

// import Angular 2
import {Component} from "@angular/core";

// import Angular 2 Component Router
// reference: http://blog.thoughtram.io/angular/2015/06/16/routing-in-angular-2.html
import {ROUTER_DIRECTIVES, Route, RouterOutlet, RouterLink, Router} from "@angular/router";

// app components

// app services
import {appServicesInjectables} from "./services/services";

@Component({
  selector: "ms-home",
  templateUrl: "core/app.template.html", // template: "<router-outlet></router-outlet>",
  directives: [RouterOutlet, RouterLink]
  , providers: appServicesInjectables
})
export class AppComponent {
  title: string;

  constructor() {
    console.log("Application bootstrapped!");
  }
}

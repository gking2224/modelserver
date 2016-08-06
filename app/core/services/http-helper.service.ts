import { Injectable } from '@angular/core';
import { Headers, Http, Response } from '@angular/http';
import {Observable} from 'rxjs/Observable';

import {
    environment, LoggingService, ErrorResponse
} from '../index';
import {ErrorObservable} from "~rxjs/observable/ErrorObservable";

@Injectable()
export class HttpHelperService {

  constructor(
        private http: Http
      , private logger: LoggingService
  ) {
  }

  doGet(r: JsonRequest): Observable<Response> {
    let url = this.insertParams(this.fullPath(r.url), r.params);
    console.log(r.headers);
    return this.http.get(url, {headers: r.headers});
  }
  doPost(r: JsonRequest): Observable<any> {
    let url = this.insertParams(this.fullPath(r.url), r.params);
    return this.http.post(url, this.bodyToJson(r.body), {headers: r.headers}).catch(this.handleError);
  }
  doPut(r: JsonRequest): Observable<any> {
    let url = this.insertParams(this.fullPath(r.url), r.params);
    return this.http.put(url, this.bodyToJson(r.body), {headers: r.headers}).catch(this.handleError);
  }
  doDelete(r: JsonRequest): Observable<any> {
    let url = this.insertParams(this.fullPath(r.url), r.params);
    return this.http.delete(url, {headers: r.headers}).catch(this.handleError);
  }

  fullPath(path : string): string {
    let rv = environment.server.baseUrl + path;
    // this.logger.debug("location: " + rv);
    return rv;
  }

  insertParams(url : string, params = {}) {
    let rv = url;
    for (let key in params) {
      rv = rv.replace(new RegExp("\\$" + key, "g"), params[key]);
    }
    return rv;
  }

  handleError(error: any): ErrorObservable {
    // this.logger.error("in handleError");
    if (error instanceof Response) {
      let e = ErrorResponse.fromJson(error.json());
      this.logger.error(e);
      return Observable.throw(e);
    }
    else {
      let msg = (error.message) ? error.message : error.status;
      let e = new ErrorResponse(500, msg);
      // this.logger.error(e);
      return Observable.throw(e);
    }
  }

  toError(error: any): ErrorResponse {
    if (error instanceof Response) {
      return ErrorResponse.fromJson(error.json());
    }
    else {
      let msg = (error.message) ? error.message : error.status;
      return new ErrorResponse(500, msg);
    }
  }

  bodyToJson(body: any) {
    return JSON.stringify(body);
  }
}

export let jsonRequestHeaders = new Headers({
  'Accept': 'application/json'
  , 'Content-Type': 'application/json'
});

export let requestHeaders = new Headers({
  'Accept': 'application/json'
  , 'Content-Type': 'application/x-www-form-urlencoded'
});

export class JsonRequest {
  url : string;
  params : {};
  body: any;
  headers: Headers;

  constructor(url: string, params: {}, body: any, headers: Headers) {
    this.url = url;
    this.params = params;
    this.body = body;
    this.headers = headers;
  }
}

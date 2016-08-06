import { Injectable } from '@angular/core';

@Injectable()
export class AutoSuggestURLs {

  static SUGGEST =   "/autosuggest/type/$type/input/$input";

  static insertParams(url : string, params : {}) {
    let rv = url;
    for (let key in params) {
      rv = rv.replace(new RegExp("\\$" + key, "g"), params[key]);
    }
    return rv;
  }

  constructor() { }

}

import { Injectable } from '@angular/core';

@Injectable()
export class ModelServiceURLs {

  static GET_ALL_TYPES = "/model/admin/types";

  static GET_MANIFESTS_BY_TYPE = "/model/admin/manifests/type/$type";

  static GET_MODEL_VERSIONS_BY_MANIFEST = "/model/admin/models/manifest/$manifestId";

  static GET_MODEL_BY_TYPE_NAME_AND_VERSION = "/model/admin/model/type/$type/name/$name/version/$version";
  static GET_MANIFEST_BY_ID = "/model/admin/manifest/$id";
  static GET_MODEL_BY_ID = "/model/admin/model/$id";

  static NEW_MODEL_TYPE = "/model/admin/types";
  static NEW_MODEL_MANIFEST = "/model/admin/manifests";
  static NEW_MODEL = "/model/admin/models";

  static EXECUTE_MODEL = "/model/execute";

  static insertParams(url : string, params : {}) {
    let rv = url;
    for (let key in params) {
      rv = rv.replace(new RegExp("\\$" + key, "g"), params[key]);
    }
    return rv;
  }

  constructor() { }

}

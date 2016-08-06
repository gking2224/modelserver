import { Injectable } from '@angular/core';
import { Headers, Http, Response } from '@angular/http';

import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/observable/throw';

import {Observable} from 'rxjs/Observable';

import { ModelServiceURLs } from './model-service-urls.service';
import {jsonRequestHeaders, JsonRequest, HttpHelperService } from '../http-helper.service';

import {
  environment, LoggingService,
    ModelType, ModelManifest, Model, ModelVersion, InputVariable, ModelExecutionResponse, ModelCreationRequest
} from '../../index';
import {ErrorResponse} from "../../entity/error-response";

@Injectable()
export class ModelService {

  constructor(
    private http: Http
    , private logger : LoggingService
    , private httpService : HttpHelperService
  ) {
  }

  getModelTypes(): Observable<ModelType[]> {
    return this.httpService.doGet(new JsonRequest(ModelServiceURLs.GET_ALL_TYPES, {}, null, jsonRequestHeaders))
      .map((res) => ModelType.arrayFromJson(res.json()))
      .catch((e) => this.httpService.handleError(e));
  }

  getModelManifests(type: ModelType): Observable<ModelManifest[]> {
    return this.httpService.doGet(new JsonRequest(ModelServiceURLs.GET_MANIFESTS_BY_TYPE, {"type": type.name}, null, jsonRequestHeaders))
        .map((res) => ModelManifest.arrayFromJson(res.json()))
        .catch((e) => this.httpService.handleError(e));
  }

  getManifestById(id: number): Observable<ModelManifest> {
    return this.httpService.doGet(
        new JsonRequest(ModelServiceURLs.GET_MANIFEST_BY_ID,
            {"id": id}, null, jsonRequestHeaders))
        .map((res) => ModelManifest.fromJson(res.json()))
        .catch((e) => this.httpService.handleError(e));
  }
  newModelManifest(type : ModelType, name : string): Observable<ModelManifest> {
    return this.httpService.doPost(
        new JsonRequest(ModelServiceURLs.NEW_MODEL_MANIFEST, {}, new ModelManifest(type, name), jsonRequestHeaders))
      .map(r => ModelManifest.fromJson(r.json()))
      .catch((e) => this.httpService.handleError(e));
  }
  deleteModelManifest(manifest: ModelManifest): Observable<void> {

    return this.httpService.doDelete(
        new JsonRequest(manifest.location, {'id': manifest.id}, manifest, jsonRequestHeaders)
    );
  }

  getModelVersions(manifest : ModelManifest): Observable<ModelVersion[]> {
    return this.httpService.doGet(
        new JsonRequest(ModelServiceURLs.GET_MODEL_VERSIONS_BY_MANIFEST,
            {"manifestId": manifest.id}, null, jsonRequestHeaders))
        .map((res) => ModelVersion.arrayFromJson(res.json()))
        .catch((e) => this.httpService.handleError(e));
  }

  getModelById(id: number): Observable<Model> {
    return this.httpService.doGet(
        new JsonRequest(ModelServiceURLs.GET_MODEL_BY_ID,
            {"id": id}, null, jsonRequestHeaders))
        .map((res) => Model.fromJson(res.json()))
        .catch((e) => this.httpService.handleError(e));
  }

  getModelByTypeNameVersion(type : ModelType, manifest : ModelManifest, version: ModelVersion): Promise<Model> {
    let headers = new Headers({
        'Accept': 'application/json'
      });

    let url = ModelServiceURLs.insertParams(
        ModelServiceURLs.GET_MODEL_BY_TYPE_NAME_AND_VERSION,
        {type: type.name, name: manifest.name, version: version.getUrlString()}
      );
    return this.http.get(this.DEPRECATED_fullPath(url), { headers: headers })
      .toPromise().then(r => Model.fromJson(r.json()))
      .catch(e => this.DEPRECATED_logError(e));
  }

  newModelType(name : string): Observable<ModelType> {
    return this.httpService.doPost(
        new JsonRequest(ModelServiceURLs.NEW_MODEL_TYPE, {}, new ModelType(name), jsonRequestHeaders))
        .map(r => ModelType.fromJson(r.json()))
        .catch((e) => this.httpService.handleError(e));
  }

  newModel(model: Model, majorIncrement : boolean): Observable<Model> {
    let mcr = new ModelCreationRequest(model, majorIncrement);
    return this.httpService.doPost(
        new JsonRequest(ModelServiceURLs.NEW_MODEL, {}, mcr, jsonRequestHeaders))
        .map(r => Model.fromJson(r.json()))
        .catch((e) => this.httpService.handleError(e));
  }

  updateModel(model: Model): Observable<Model> {
    return this.httpService.doPut(
        new JsonRequest(model.location, {}, model, jsonRequestHeaders))
        .map(r => Model.fromJson(r.json()))
        .catch((e) => this.httpService.handleError(e));
  }

  // getModelById(id : number): Promise<Model> {
  //   let headers = new Headers({
  //     'Accept': 'application/json'
  //     });
  //
  //   let url = ModelServiceURLs.insertParams(ModelServiceURLs.GET_MODEL_BY_ID, {id : id});
  //   return this.http.get(this.DEPRECATED_fullPath(url), {headers: headers})
  //     .toPromise().then(r => Model.fromJson(r.json()))
  //     .catch(e => this.DEPRECATED_logError(e));
  // }

  DEPRECATED_handleError(error: any) {
    if (error instanceof Response) {
      return Observable.throw(ErrorResponse.fromJson(error.json()));
    }
    else {
      let msg = (error.message) ? error.message : error.status;
      // this.logger.error(msg);
      return Observable.throw(msg);
    }
  }

  DEPRECATED_logError(error : any) {
     // this.logger.error("Got error:");
  }

  executeModel(model : Model, inputs : InputVariable[]): Promise<ModelExecutionResponse> {
    let headers = new Headers({
      'Accept': 'application/json'
      , 'Content-Type': 'application/json'
      });
    let params = {};
    for (let v of inputs) {
      params[v.variable.name] = v.value;
    }
    let body = JSON.stringify({modelId: model.id, inputParams: params});
    // this.logger.debug("Executing model with json: " + body);
    return this.http.post(this.DEPRECATED_fullPath(ModelServiceURLs.EXECUTE_MODEL), body, {headers: headers})
      .toPromise().then(r => ModelExecutionResponse.fromJson(r.json()))
      .catch(e => this.DEPRECATED_logError(e));
  }

  DEPRECATED_fullPath(path : string) {
    let rv = environment.server.baseUrl + path;
    // this.logger.debug("location: " + rv);
    return rv;
  }


  deleteModelType(type: ModelType): Observable<void> {

    return this.httpService.doDelete(
        new JsonRequest(type.location, {'id': type.id}, type, jsonRequestHeaders)
    );
  }

  updateModelType(type: ModelType): Observable<ModelType> {

    return this.httpService.doPut(
        new JsonRequest(type.location, {}, type, jsonRequestHeaders)
    );
  }
}

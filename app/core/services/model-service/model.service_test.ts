/* tslint:disable:no-unused-variable */
import {provide} from '@angular/core';
import {
  BaseRequestOptions, Response, ResponseOptions, Http, Headers, HTTP_PROVIDERS
} from '@angular/http';

import {
  addProviders, inject, async
} from '@angular/core/testing';

import { MockBackend, MockConnection } from '@angular/http/testing';

import { ModelService } from './model.service';
import { LoggingService } from '../logging.service';
import { ModelType, ModelManifest, Model, ModelVersion, InputVariable, ModelExecutionResponse } from '../../entity/model-entities';

describe('Service: ModelService', () => {
  beforeEach(() => {
    addProviders([
      ModelService, LoggingService
      , MockBackend, BaseRequestOptions
      , {
        provide: Http,
        useFactory: (
            mockbackend: MockBackend
            , defaultOptions: BaseRequestOptions
        ) => {
          return new Http(mockbackend, defaultOptions);
        },
        deps: [MockBackend , BaseRequestOptions]
        }
    ]);
  });
  
  it('should create ModelService',
    inject([ModelService],
      (service: ModelService) => {
        expect(service).toBeTruthy();
      }));
  
  it('should return types from getModelTypes()',
    async(inject([
        ModelService
        , MockBackend
        , LoggingService
    ], (
        modelService : ModelService
        , mockBackend : MockBackend
        , logger : LoggingService
    ) => {
      const expectedUrl = 'http://localhost:8080/modelserver/model/admin/type/all';
      mockBackend.connections.subscribe((conn: MockConnection) => {
        expect(conn.request.url).toBe(expectedUrl);
        let headers = new Headers({'Content-Type': 'application/json'});
        let mockResponse = new ResponseOptions({
          body: JSON.stringify([{"id": 1, "name": "Demonstration"}])
          , status: 200, statusText: "OK", headers: headers
        });
        let r = new Response(mockResponse);
        conn.mockRespond(r);
      });
      
      let result: any;
      modelService.getModelTypes()
        .subscribe(
          (res) => result = res,
          (err) => this.logger.error(err)
        );
      
      expect(result).toBeTruthy();
      expect(result.length).toBe(1);
      expect(result[0].name).toBe("Demonstration");
    })));
  
  it('should return types from getModelManifests()',
    async(inject([
        ModelService
        , MockBackend
        , LoggingService
    ], (
        modelService : ModelService
        , mockBackend : MockBackend
        , logger : LoggingService
    ) => {
      const expectedUrl = 'http://localhost:8080/modelserver/model/admin/manifest/bytype/Demonstration';
      mockBackend.connections.subscribe((conn: MockConnection) => {
        expect(conn.request.url).toBe(expectedUrl);
        let headers = new Headers({'Content-Type': 'application/json'});
        let mockResponse = new ResponseOptions({
          body: JSON.stringify([{
            "id": 1,
            "name": "Kitchen Sink",
            "type": {"id": 1, "name": "Demonstration"}
          }])
          , status: 200, statusText: "OK", headers: headers
        });
        conn.mockRespond(new Response(mockResponse));
      });
      
      let result: any;
      let mt = new ModelType("Demonstration");
      modelService.getModelManifests(mt)
        .subscribe(
          (res) => result = res,
          (err) => this.logger.error(err)
        );
      
      expect(result).toBeTruthy();
      expect(result.length).toBe(1);
      expect(result[0].name).toBe("Kitchen Sink");
    })));

});

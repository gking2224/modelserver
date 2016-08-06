/* tslint:disable:no-unused-variable */
import {provide} from '@angular/core';
import {
    BaseRequestOptions
  , Response
  , ResponseOptions
  , ConnectionBackend
  , Http
} from '@angular/http';

import {
  addProviders
  , inject
} from '@angular/core/testing';
import {MockBackend} from '@angular/http/testing';

import { AutoSuggestService } from './auto-suggest.service';
import { LoggingService } from '../../core/index';

describe('Service: AutoSuggest', () => {
  beforeEach(() => {
    addProviders([
      AutoSuggestService
    , LoggingService
    , MockBackend
    , BaseRequestOptions
    , {
        provide: Http,
        useFactory: (
            mockbackend: ConnectionBackend,
            defaultOptions: BaseRequestOptions
        ) => {
          return new Http(mockbackend, defaultOptions);
        },
        deps: [MockBackend, BaseRequestOptions]
      }
    ]);
  });

  it('should create service',
    inject([AutoSuggestService],
      (service: AutoSuggestService) => {
        expect(service).toBeTruthy();
      }));

  it('should return list of >0 suggestions for nature',
    inject([AutoSuggestService],
      (service: AutoSuggestService) => {
        let s = service.getSuggestions("nature", "g");
        expect(s).toBeTruthy();
        expect(s.length).toBeGreaterThan(0);
      }));

  it('should return empty list of suggestions for unknown type',
    inject([AutoSuggestService],
      (service: AutoSuggestService) => {
        let s = service.getSuggestions("unknown", "g");
        expect(s).toBeTruthy();
        expect(s.length).toEqual(0);
      }));
});

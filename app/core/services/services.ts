"use strict";
import {ModelService} from './model-service/model.service';
import {LoggingService} from '../services/index';
import {ModelServiceURLs} from './index';
import {AutoSuggestService} from '../../modules/auto-suggest/index';
import {HttpHelperService} from "./http-helper.service";

// way to create a list all injectable classes
export let appServicesInjectables: Array<any> = [
    ModelService
  , LoggingService
  , ModelServiceURLs
  , AutoSuggestService
  , HttpHelperService
];

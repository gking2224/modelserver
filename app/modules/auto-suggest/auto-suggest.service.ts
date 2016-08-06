import { Injectable } from "@angular/core";
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { AutoSuggestURLs } from './auto-suggest-urls.service';
import { environment, LoggingService } from "../../core/index";


@Injectable()
export class AutoSuggestService {

  suggestions : {};
  constructor(
    private logger : LoggingService
  ) {
    this.suggestions = {
      'nature': ['groovy.class.loader.v1', 'groovy.class.loader.v2', 'groovy.shell.v1'],
      'variableTypes': ['int', 'float', 'string', 'date', 'char'],
      'variableNames': ['a', 'b', 'n', 'n1', 'n2', 'log.level']};
  }
  
  getSuggestions(type: string, value: string) {
    let allForType = this.suggestions[type] || [];
    return allForType.filter((s: string) => s.indexOf(value) === 0).sort();
  }

}

/* tslint:disable:no-unused-variable */

import { addProviders, async, inject } from '@angular/core/testing';
import { AutoSuggestDirective } from './auto-suggest.directive';

describe('Directive: AutoSuggest', () => {
  it('should create an instance', () => {
    let directive = new AutoSuggestDirective(null, null, null, null);
    expect(directive).toBeTruthy();
  });
});

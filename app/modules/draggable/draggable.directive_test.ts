/* tslint:disable:no-unused-variable */

import { addProviders, async, inject } from '@angular/core/testing';
import { DraggableDirective } from './draggable.directive';

describe('Directive: Draggable', () => {
  it('should create an instance', () => {
    let directive = new DraggableDirective(null, null, null);
    expect(directive).toBeTruthy();
  });
});

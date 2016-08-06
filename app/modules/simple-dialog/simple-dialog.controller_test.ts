/* tslint:disable:no-unused-variable */

import { addProviders, async, inject } from '@angular/core/testing';
import { SimpleDialogComponent } from './simple-dialog.controller';
import {LoggingService} from "../../core/index";

describe('Component: SimpleDialogComponent', () => {
  it('should be created', () => {
    let simpleDialog = new SimpleDialogComponent(new LoggingService());
    expect(simpleDialog).toBeTruthy();
  });
});

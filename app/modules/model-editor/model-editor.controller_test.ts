/* tslint:disable:no-unused-variable */

import { addProviders, async, inject } from '@angular/core/testing';
import { ModelEditorComponent } from './model-editor.controller';
import {LoggingService} from "../../core/index";

describe('Component: ModelEditorComponent', () => {
  it('should be created', () => {
    let modelEditor = new ModelEditorComponent(new LoggingService());
    expect(modelEditor).toBeTruthy();
  });
});

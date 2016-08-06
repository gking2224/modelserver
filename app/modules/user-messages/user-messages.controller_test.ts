/* tslint:disable:no-unused-variable */

import { UserMessagesComponent } from './user-messages.controller';
import { LoggingService } from '../../core/index';

describe('Component: UserMessages', () => {
  it('should create an instance', () => {
    let component = new UserMessagesComponent(new LoggingService());
    expect(component).toBeTruthy();
  });
});

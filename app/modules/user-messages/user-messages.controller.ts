import {
  Component, OnInit
 } from '@angular/core';

import { LoggingService } from '../../core/index';

import {MessageNotDisabledPipe} from './user-message.pipe';
import {UserMessageComponent} from "./user-message.controller";
import {UserMessage} from "./user-message";
@Component({
  moduleId: module.id
  , selector: 'ms-user-messages'
  , templateUrl: 'user-messages.template.html'
  , styleUrls: ['user-messages.scss']
  , pipes: [MessageNotDisabledPipe]
  , directives: [UserMessageComponent]
})
export class UserMessagesComponent implements OnInit {

  messages : UserMessage[] = [];
  
  constructor(
    private logger : LoggingService
  ) {
  }

  ngOnInit() {
    this.logger.registerUserMessageListener((m : UserMessage) => {this.displayUserMessage(m); });
  }
  
  displayUserMessage(m : UserMessage) {
    this.messages.push(m);
    setTimeout(() => this.closeMessage(m), 8000);
  }

  closeMessage(message: UserMessage) {
    message.disable();
    setTimeout(() => this.messages = this.messages.filter( (x) => x.id !== message.id), 1000);
  }
}

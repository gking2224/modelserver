import {
    Component, OnInit, Input,
    trigger, state, style, transition, animate,
    Pipe, PipeTransform, Injectable, EventEmitter, Output
} from '@angular/core';

import { LoggingService } from '../../core/index';
import {NgClass} from "@angular/common";
import {UserMessage, MessageType} from "./user-message";

@Component({
  moduleId: module.id
  , selector: 'ms-user-message'
  , templateUrl: "user-message.template.html"
  , animations: [
    trigger('msgState', [
      state('enabled', style({height: '*', padding: '20px', transform: 'translateY(0)'})),
      state('disabled', style({height: '0', padding: '20px', transform: 'translateY(100%)'})),
      state('void', style({height: '0', padding: '20px', transform: 'translateY(100%)'})),
      transition('void => enabled', [
        animate('600ms')
      ]),
      transition('enabled => disabled', [
        animate('600ms')
      ])
    ])
  ]
  , directives: [NgClass]
})
export class UserMessageComponent implements OnInit {

  @Input() message: UserMessage;
  messageType : string;
  @Output() close = new EventEmitter<UserMessage>();

  constructor(
    private logger : LoggingService
  ) {
  }

  ngOnInit() {
    this.messageType = MessageType[this.message.type];
  }

  closeMessage() {
    this.close.emit(this.message);
  }
}

import {Injectable} from '@angular/core';
import {MessageType, UserMessage} from "../../modules/user-messages/user-message";

// import {dateFormat} from '../../typings/custom.d.ts';

declare var dateFormat: any;

@Injectable()
export class LoggingService {

  userMessageListeners: ((u: UserMessage) => void)[] = [];

  constructor() {
  }

  trace(message: any) {
    this.log(message, LogLevel.TRACE);
  }

  debug(message: any) {
    this.log(message, LogLevel.DEBUG);
  }

  info(message: any) {
    this.log(message, LogLevel.INFO);
  }

  warn(message: any) {
    this.log(message, LogLevel.WARN);
  }

  error(message: any) {
    this.log(message, LogLevel.ERROR);
  }

  log(message: any, level: LogLevel) {

    let msg = this.formatMessage(message, level);
    console.log(msg);
  }

  formatMessage(message: any, level: LogLevel) {
    let now = new Date();

    let dateStr = dateFormat(now, "yyyy-mm-dd HH:MM:ss");
    let timeStr = now.toLocaleTimeString();
    let msg = JSON.stringify(message);
    return dateStr + " " + this.levelAsString(level) + " " + msg;
  }

  levelAsString(level: LogLevel) {
    switch (level) {
      case LogLevel.TRACE:
        return "TRACE";
      case LogLevel.DEBUG:
        return "DEBUG";
      case LogLevel.INFO:
        return "INFO";
      case LogLevel.WARN:
        return "WARN";
      case LogLevel.ERROR:
        return "ERROR";
      default:
        return "<UNKNOWN LEVEL>";
    }
  }

  userInfoMessage(message: string) {
    this.userMessage(message, MessageType.INFO);
  }

  userWarnMessage(message: string) {
    this.userMessage(message, MessageType.WARN);
  }

  userErrorMessage(message: string) {
    this.userMessage(message, MessageType.ERROR);
  }

  userSuccessMessage(message: string) {
    this.userMessage(message, MessageType.SUCCESS);
  }

  userMessage(message: string, type: MessageType = MessageType.INFO) {
    for (let l of this.userMessageListeners) {
      l(new UserMessage(message, type));
    }
  }

  registerUserMessageListener(l: (u: UserMessage) => void) {
    this.userMessageListeners.push(l);
  }
}
export enum LogLevel {
  TRACE = 1,
  DEBUG,
  INFO,
  WARN,
  ERROR
}

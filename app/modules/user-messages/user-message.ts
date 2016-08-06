export class UserMessage {
  static idx : number = 0;

  id : number;
  message : string;
  type : MessageType;
  state: string = 'enabled';
  disabled : boolean = false;

  constructor(message : string, type: MessageType = MessageType.INFO) {
    this.message = message;
    this.type = type;
    this.id = UserMessage.idx++;
  }
  disable() {
    // this.disabled = true;
    this.state = "disabled";
  }
}

export enum MessageType {
  SUCCESS = 1,
  INFO,
  WARN,
  ERROR
}

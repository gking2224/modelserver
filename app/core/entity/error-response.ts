export class ErrorResponse {
  errorCode : number;
  errorMessage : string;

  static fromJson(j: any): ErrorResponse {
    let rv = new ErrorResponse(null, null);
    rv.errorCode = j.errorCode;
    rv.errorMessage = j.errorMessage;
    return rv;
  }

  constructor(errorCode: number, errorMessage: string) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  formatMessage(msg: string): string {
    return msg + ": " + this.errorCode + ": " + this.errorMessage;
  }
}

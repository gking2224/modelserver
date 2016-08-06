
export class ModelType {
  location: string;
  id: number;
  name: string;
  enabled: boolean;

  static fromJson(json : any) {
    let rv = new ModelType(json.name);
    rv.id = json.id;
    rv.location = json.location;
    rv.enabled = json.enabled;
    return rv;
  }

  static arrayFromJson(json : any) {
    if (json == null) { return []; }
    let rv: ModelType[] = [];
    for (let j of json) {
      rv.push(ModelType.fromJson(j));
    }
    return rv;
  }

  constructor(name: string) {
    this.name = name;
  }
}

export class ModelManifest {
  id: number;
  name: string;
  location: string;
  type: ModelType;
  
  static fromJson(json : any) {
    let rv = new ModelManifest(null, null);
    rv.id = json.id;
    rv.name =  json.name;
    rv.location = json.location;
    rv.type = ModelType.fromJson(json.type);
    return rv;
  }

  static arrayFromJson(json : any) {
    if (json == null) { return []; }
    let rv: ModelManifest[] = [];
    for (let j of json) {
      rv.push(ModelManifest.fromJson(j));
    }
    return rv;
  }

  constructor(type : ModelType = undefined, name : string = undefined) {
    this.type = type;
    this.name = name;
  }
}


export class ModelVariable {
  name : string;
  type : string;
  mandatory : boolean = false;
  direction : string = "IN";
  
  static fromJson(json : any) {
    let rv = new ModelVariable();
    rv.name = json.name;
    rv.type =  json.type;
    rv.mandatory = json.mandatory;
    rv.direction = json.direction;
    return rv;
  }

  clone(): ModelVariable {
    let clone = new ModelVariable();
    clone.name = this.name;
    clone.type = this.type;
    clone.mandatory = this.mandatory;
    clone.direction = this.direction;
    return clone;
  }

  compareTo(other : ModelVariable) {
    if (other.direction !== this.direction) {
      return (this.direction < other.direction) ? -1 : 1;
    }
    else if (other.mandatory !== this.mandatory) {
      return (other.mandatory) ? 1 : -1;
    }
    else if (other.name !== this.name) {
      return (this.name < other.name) ? -1 : 1;
    }
    else { return 0; }
  }
    
}

export class ModelVersion {
  majorVersion : number;
  minorVersion : number;
  location: string;
  enabled: boolean;

  static arrayFromJson(json : any) {
    if (json == null) { return []; }
    let rv: ModelVersion[] = [];
    for (let j of json) {
      rv.push(ModelVersion.fromJson(j));
    }
    return rv;
  }
  
  static fromJson(json : any) {
    let rv = new ModelVersion(null, null);
    rv.majorVersion = json.majorVersion;
    rv.minorVersion =  json.minorVersion;
    rv.location = json.location;
    rv.enabled = json.enabled;
    return rv;
  }

  constructor(majorVersion : number, minorVersion : number) {
    this.majorVersion = majorVersion;
    this.minorVersion = minorVersion;
  }

  getDisplayString() {
    return this.concat(".");
  }

  getUrlString() {
    return this.concat("_");
  }

  concat(separator : string) {
    return this.majorVersion + separator + this.minorVersion;
  }
  
  compareTo(other : ModelVersion) {
    if (other.majorVersion === this.majorVersion) {
      return this.minorVersion - other.minorVersion;
    }
    else { return this.majorVersion - other.majorVersion; }
  }
}


export class Model {
  id : number = -1;
  manifest : ModelManifest;
  script : string = "";
  natures : string[] = [];
  version : ModelVersion;
  variables : ModelVariable[] = [];
  enabled : boolean = true;
  location : string;

  static fromJson(json : any) {
    let rv = new Model(ModelManifest.fromJson(json.manifest));
    rv.id = json.id;
    rv.version = ModelVersion.fromJson(json.version);
    rv.natures = json.natures;
    rv.script = json.script;
    rv.location = json.location;
    rv.enabled = json.enabled;
    rv.variables = [];
    for (let j of json.variables) {
      rv.variables.push(ModelVariable.fromJson(j));
    }
    return rv;
  }

  constructor(manifest: ModelManifest) {
    this.manifest = manifest;
  }

  addVariable(v : ModelVariable) {
      this.variables.push(v);
  }
} 

export class InputVariable {
  variable : ModelVariable;
  value : any;

  constructor(variable: ModelVariable, value: any) {
    this.variable = variable;
    this.value = value;
  }
}

export class ModelExecutionResponse {
  success : boolean = false;
  durationInNano : number;
  warnings : ModelExecutionWarning[];
  outputs : ModelResponseOutput[];
  stdout : string;
  stderr : string;
  error : ModelExecutionError;
  executorClassName : string;
  requestTime : string;

  static fromJson(json : any) {
    return (!json) ? null : new ModelExecutionResponse(
      json.success,
      json.durationInNano,
      ModelExecutionWarning.arrayFromJson(json.warnings),
      ModelResponseOutput.arrayFromJson(json.outputs),
      json.stdout,
      json.stderr,
      ModelExecutionError.fromJson(json.error),
      json.executorClassName,
      json.requestTime);
  }

  constructor(
    success : boolean
    , durationInNano : number
    , warnings : ModelExecutionWarning[]
    , outputs : ModelResponseOutput[]
    , stdout : string
    , stderr : string
    , error : ModelExecutionError
    , executorClassName : string
    , requestTime : string
  ) {
    this.success =        success;
    this.durationInNano = durationInNano;
    this.warnings =       warnings;
    this.outputs =        outputs;
    this.stdout =         stdout;
    this.stderr =         stderr;
    this.error =          error;
    this.executorClassName = executorClassName;
    this.requestTime = requestTime;
  }
}

export class ModelResponseOutput {
  key : string;
  value : any;

  static arrayFromJson(json : any) {
    if (!json) { return []; }
    let rv: ModelResponseOutput[] = [];
    for (let i in json) {
      rv.push(new ModelResponseOutput(i, json[i]));
    }
    return rv;
  }

  constructor(key: string, value: any) {
    this.key = key;
    this.value = value;
    this.value = value;
  }
}

export class ModelExecutionError {
  
  code : number;
  message : string;
  exceptionClass : string;
  stackTrace : string;

  static fromJson(json : any) {
    return (!json) ? null : new ModelExecutionError(
      json.code, json.message, json.exceptionClass, json.stackTace);
  }

  constructor(code : number, message : string, exceptionClass : string, stackTrace : string) {
    this.code = code;
    this.message = message;
    this.exceptionClass = exceptionClass;
    this.stackTrace = stackTrace;
  }
}

export class ModelExecutionWarning {
  code : number;
  summary : string;
  detail : string;

  static arrayFromJson(json : any) {
    if (!json) { return []; }
    let rv: ModelExecutionWarning[] = [];
    for (let j of json) {
      rv.push(ModelExecutionWarning.fromJson(j));
    }
    return rv;
  }

  static fromJson(json : any) {
    return (!json) ? null : new ModelExecutionWarning(
      json.code, json.summary, json.detail);
  }

  constructor(code: number, summary: string, detail: string) {
    this.code = code;
    this.summary = summary;
    this.detail = detail;
  }
}

export class ModelCreationRequest {
  entity : Model;
  incrementType: string;

  constructor(model : Model, majorIncrement: boolean) {
    this.entity = model;
    this.incrementType = (majorIncrement) ? 'MAJOR' : 'MINOR';
  }
}

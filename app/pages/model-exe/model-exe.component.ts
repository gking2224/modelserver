import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { ModelService } from '../../core/services/index';

import { LoggingService, Model, ModelVariable, InputVariable, ModelExecutionResponse, ModelResponseOutput } from '../../core/index';

import { ToggleDirective } from '../../modules/directives';
import { ModelSummaryComponent, UserMessagesComponent } from '../../modules/components';
import {ErrorResponse} from "../../core/entity/error-response";

@Component({
  moduleId: module.id
  , selector: 'page-ms-exe',
  templateUrl: 'model-exe.component.html',
  styleUrls: ['model-exe.component.scss'],
  directives: [ModelSummaryComponent, ToggleDirective, UserMessagesComponent],
})
export class ModelExecutorComponent implements OnInit {

  model : Model;
  inputVariables : InputVariable[] = [];
  declaredOutVariables : ModelResponseOutput[] = [];
  undeclaredOutVariables : ModelResponseOutput[] = [];
  sub : any;
  response : ModelExecutionResponse;
  
  constructor(
    private router: Router
    , private modelService: ModelService
    , private route: ActivatedRoute
    , private logger : LoggingService
    ) {
  }
  
  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      if (params['id'] !== undefined) {
        let id = +params['id'];
        this.logger.debug("Loading model " + id);
        this.loadModel(id);
      }
      else {
        this.router.navigate(['/browser']);
      }
    });
  }
  
  loadModel(id : number) {
    
    this.modelService.getModelById(id)
        .subscribe(
            m => {
              this.model = m;
              this.addLogLevelVariable(this.model);
              this.setInitialState();
            },
            (e: ErrorResponse) => this.logger.userErrorMessage(e.formatMessage("Could not load model")));
  }
  
  addLogLevelVariable(m : Model) {
//    m.addVariable(ModelVariable.fromJson({name:'log.level', type:'java.lang.String', mandatory:false, direction:'IN'}));
  }
  
  setInitialState() {
    this.inputVariables = [];
    let inVars = this.model.variables.filter(v =>
      v.direction !== "OUT"
    );
    for (let i of inVars) {
      this.inputVariables.push(new InputVariable(i, null));
    }
    this.inputVariables = this.inputVariables.sort((i1, i2) => i1.variable.compareTo(i2.variable));
  }

  execute() {
    this.modelService.executeModel(this.model, this.inputVariables)
      .then(r => this.processResponse(r));
  }

  processResponse(r : ModelExecutionResponse) {
    this.response = r;
    this.captureDeclaredOutVariables();
  }

  captureDeclaredOutVariables() {
    let varDirections = {}; // map of direction -> declared variable name 
    for (let v of this.model.variables) { varDirections[v.name] = v.direction; }
    this.declaredOutVariables = [];
    this.undeclaredOutVariables = [];
    for (let v of this.response.outputs) {
      let decl = varDirections[v.key];
      if (decl == null || "IN" === decl) { this.undeclaredOutVariables.push(v); }
      if (decl != null && "IN" !== decl) { this.declaredOutVariables.push(v); }
    }
    this.logger.debug(this.declaredOutVariables);
    this.logger.debug(this.undeclaredOutVariables);
  }

  browser() {
    this.router.navigate(['/browser', this.model.id]);
  }
  edit() {
    this.router.navigate(['/model', this.model.id]);
  }
}

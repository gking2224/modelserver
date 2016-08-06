import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Router, ActivatedRoute, ROUTER_DIRECTIVES } from '@angular/router';

import { ModelService } from '../../core/services/index';

import { ModelType, ModelManifest, Model, ModelVersion, ModelVariable, LoggingService } from '../../core/index';

import { SimpleDialogComponent, ModelSummaryComponent, UserMessagesComponent, ModelEditorComponent }
  from '../../modules/components';
import { ToggleDirective , DraggableDirective, AutoSuggestDirective} from '../../modules/directives';
import {ErrorResponse} from "../../core/entity/error-response";

@Component({
    moduleId: module.id
    , selector: 'page-ms-model'
    , templateUrl: 'model.component.html'
    , styleUrls: ['model.component.scss']
    , directives: [
      ModelSummaryComponent
      , ModelEditorComponent
      , SimpleDialogComponent
      , ToggleDirective
      , UserMessagesComponent
      , DraggableDirective
      , AutoSuggestDirective
    ]
})
export class ModelComponent implements OnInit {

  modelId : number;
  model : Model;

  paramName : string;
  sub : any;
  navigated : boolean;
  saveMajorIncrement : boolean = false;

  doingUpsertNature : boolean = false;
  updatedNature : string;

  doingUpsertVariable : boolean = false;
  upsertedVariable : ModelVariable;
  updatedVariable : ModelVariable;

  directionValues : string[] = ['IN', 'OUT', 'INOUT'];

  // START LIFECYCLE
  constructor(
      private router: Router
      , private modelService: ModelService
      , private route: ActivatedRoute
      , private logger: LoggingService
  ) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      if (params['action'] !== undefined && params['action'] === "new") {
        this.modelService.getManifestById(params['id'])
            .subscribe(
                mf => this.model = new Model(mf),
                (e: ErrorResponse) => this.logger.userErrorMessage(e.formatMessage("Could not load model")));
      }
      else if (params['id'] !== undefined) {
        let id = +params['id'];
        this.modelId = id;
        this.reset();
      }
      else {
        this.logger.userErrorMessage("Could not load page - no model id!");
        // this.router.navigate(['/browser']);
      }
    });
  }
  setInitialState() {
    this.sortVariables();
    this.sortNatures();
  }
  // END LIFECYCLE


  // START NAVIGATION
  browser() {
    this.router.navigate(['/browser', this.model.id]);
  }
  save() {
    this.modelService.newModel(this.model, this.saveMajorIncrement)
      .subscribe(
        (m) => {
          this.logger.userSuccessMessage("Model saved successfully");
          this.model = m;
        },
        (e: ErrorResponse) => {
          this.logger.userErrorMessage(e.formatMessage("Model could not be saved"));
        });
  }
  execute() {
    this.router.navigate(['/modelexe/', this.model.id]);
  }
  reset() {
      this.modelService.getModelById(this.modelId)
          .subscribe(
              (m) => { this.model = m; this.setInitialState(); },
              (e: ErrorResponse) => this.logger.userErrorMessage(e.formatMessage("Could not load model '" + this.modelId + "'")));

    ;;
  }
  // END NAVIGATION

  // START NATURE
  completeUpsertNature(upsertedNature: string) {
    this.logger.debug(upsertedNature);
    this.logger.debug(this.updatedNature);
    if (upsertedNature === "" || upsertedNature == null) {
      this.logger.userMessage("Cannot save empty nature");
    }
    else {
      if (this.model.natures.indexOf(upsertedNature) !== -1) {
        this.logger.userMessage("Cannot add duplicate nature");
      }
      else {
        this.model.natures.push(upsertedNature);
        this.sortNatures();
        if (this.updatedNature != null) {
          this.deleteNature(this.updatedNature);
        }
        this.cancelUpsertNature();
        this.logger.debug(this.model.natures);
      }
    }
  }
  upsertNature(nature: string) {
    this.doingUpsertNature = true;
    this.updatedNature = nature;
  }
  cancelUpsertNature() {
    this.updatedNature = null;
    this.doingUpsertNature = false;
  }
  deleteNature(nature: string) {
    this.logger.trace("deleting nature " + nature);
    this.model.natures = this.model.natures.filter(n => n !== nature);
  }
  sortNatures() {
    this.model.natures = this.model.natures.sort();
  }
  // END NATURE


  // START VARIABLE
  completeUpsertVariable() {
    if (
        this.upsertedVariable.name === ''
        || this.upsertedVariable.name == null
        || this.upsertedVariable.type === ""
        || this.upsertedVariable.type == null) {
      this.logger.debug("Not adding empty new var");
    }
    else if (this.model.variables.filter(v => v.name === this.upsertedVariable.name).length > 0) {
      this.logger.debug("not adding duplicate var");
    }
    else {
      this.model.variables.push(this.upsertedVariable);
      this.sortVariables();
      if (this.updatedVariable != null) {
        this.deleteVariable(this.updatedVariable);
      }
      this.cancelUpsertVariable();
    }
  }
  upsertVariable(variable : ModelVariable) { // start an upsert
    if (!this.doingUpsertVariable) {
      this.updatedVariable = variable;
      this.doingUpsertVariable = true;
      this.upsertedVariable = variable ? variable.clone() : new ModelVariable();
      this.logger.debug(JSON.stringify(this.upsertedVariable));
    }
  }
  cancelUpsertVariable() { // cancel or complete upsert
    this.updatedVariable = null;
    this.upsertedVariable = null;
    this.doingUpsertVariable = false;
  }
  deleteVariable(variable : ModelVariable) {
    this.logger.debug("deleting variable " + JSON.stringify(variable));
    this.model.variables = this.model.variables.filter(v => v.name !== variable.name);
  }
  sortVariables() {
    this.model.variables = this.model.variables.sort((v1, v2) => v1.compareTo(v2));
  }
  // END VARIABLE


  // START SCRIPT

  scriptChanged(script: any) {
    this.model.script = script;
  }
  // END SCRIPT

  // START UI
  // END UI
}

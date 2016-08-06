import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';

import { ModelService } from '../../core/services/index';
import { ModelSummaryComponent, SimpleDialogComponent, UserMessagesComponent } from '../../modules/components';

import { Model, ModelType, ModelManifest, ModelVersion, LoggingService } from '../../core/index';
import {ErrorResponse} from "../../core/entity/error-response";

@Component({
  moduleId: module.id
  , directives: [ModelSummaryComponent, SimpleDialogComponent, UserMessagesComponent]
  , selector: 'page-model-browser'
  , templateUrl: 'model-browser.template.html'
  , styleUrls: ['model-browser.scss']
})
export class ModelBrowserComponent implements OnInit {

  model : Model;
  types : ModelType[] = [];
  manifests : ModelManifest[] = [];
  versions : ModelVersion[] = [];
  selectedType : ModelType;
  selectedManifest : ModelManifest;
  selectedVersion : ModelVersion;
  addingType : boolean = false;
  newTypeName : string;
  addingManifest : boolean = false;
  newManifestName : string;
  sub : any;
  
  constructor(
      private router: Router
      , private modelService: ModelService
      , private route: ActivatedRoute
      , private logger: LoggingService
  ) {
  }
  
  ngOnInit() {
    this.logger.trace("ModelBrowserComponent.ngOnInit()");
    this.sub = this.route.params.subscribe(params => {
      if (params['id'] !== undefined) {
        let id = +params['id'];
        this.logger.debug("ModelBrowserComponent.ngOnInit() - loading model " + id);
        this.modelService.getModelById(id)
            .subscribe(
                (m: Model) => {
                  this.model = m;
                  this.setInitialState();
                },
                (e: ErrorResponse) => this.logger.userErrorMessage(e.formatMessage("Could not load model")));
      } else {
        this.model = null;
        this.setInitialState();
      }
    });
  }
  
  setInitialState() {
    this.selectedType = (this.model) ? this.model.manifest.type : null;
    this.selectedManifest = (this.model) ? this.model.manifest : null;
    this.selectedVersion = (this.model) ? this.model.version : null;
    this.manifests = (this.model) ? [this.model.manifest] : [];
    this.versions = (this.model) ? [this.model.version] : [];
    this.refreshTypes();
    this.refreshManifests();
    this.refreshVersions();
  }
  
  refreshTypes() {
    
    this.modelService.getModelTypes()
    .subscribe(
        types => this.types = types,
        e => this.logger.userErrorMessage(e.formatMessage("Error loading model groups"))
    );
  }
  
  onSelectType(type: ModelType) {
    this.manifests = [];
    this.versions = [];
    this.selectedVersion = undefined;
    this.selectedManifest = undefined;
    this.selectedType = type;
    this.model = null;
    this.refreshManifests();
  }
  
  refreshManifests() {
    if (this.selectedType) {
      this.modelService.getModelManifests(this.selectedType)
        .subscribe(
          mfs => this.manifests = mfs,
          (error : any) => this.logger.userErrorMessage("Error loading models")
        );
    }
  }
  
  onSelectManifest(mf: ModelManifest) {
    this.versions = [];
    this.model = null;
    this.selectedVersion = undefined;
    this.selectedManifest = mf;
    this.refreshVersions();
  }
  
  refreshVersions() {
    if (this.selectedManifest) {
      this.modelService.getModelVersions(this.selectedManifest)
        .subscribe(
            v => this.versions = v.sort((v1, v2) => v2.compareTo(v1)),
            e => this.logger.userErrorMessage("Error loading model versions")
        );
    }
  }
  
  editModel() {
    this.router.navigate(['/model', this.model.id]);
  }
  
  executeModel() {
    this.router.navigate(['/modelexe', this.model.id]);
  }
  
  disableVersion() {
    this.selectedVersion.enabled = false;
    this.modelService.updateModel(this.model).subscribe(
        r => this.logger.userSuccessMessage(("Model Group successfully disabled")),
        (e: ErrorResponse) => this.logger.userErrorMessage(e.formatMessage("Error disabling model")));
  }

  enableVersion() {
    this.selectedVersion.enabled = true;
    this.modelService.updateModel(this.model)
        .subscribe(r => this.logger.userSuccessMessage(("Model successfully enabled")),
        (e: ErrorResponse) => this.logger.userErrorMessage(e.formatMessage("Error enabling model")));
  }

  deleteModel() {
    // this.modelService.deleteModelType(this.selectedType)
    //     .subscribe(r => this.logger.userSuccessMessage(("Model successfully deleted")));
  }

  newModelVersion() {
    this.router.navigate(['/model/new/', this.selectedManifest.id]);
  }
  
  newModelType() {this.addingType = true; }
  cancelNewType() {this.addingType = false; this.newTypeName = null; }
  saveNewType(name : string) {
    if (name !== "" && name !== undefined) {
      this.modelService.newModelType(name)
        .subscribe(
          (mt) => {
            this.addingType = false;
            this.refreshTypes();
            this.logger.userSuccessMessage("New Model Group saved successfully");
            this.selectedType = mt;
          },
          (e: ErrorResponse) => {
            this.logger.userErrorMessage(e.formatMessage("Model Group could not be saved"));
          });
    }
    else { this.logger.userInfoMessage("Please enter a value"); }
  }
  deleteModelType() {
    this.modelService.deleteModelType(this.selectedType)
        .subscribe(r => {
          this.logger.userSuccessMessage(("Model group '" + this.selectedType.name + "' successfully deleted"));
          this.refreshTypes();
        });
  }
  
  newModelManifest() { this.addingManifest = true; }
  cancelNewManifest() {this.addingManifest = false; this.newManifestName = null; }
  saveNewManifest(name: string) {
    if (name !== "" && name !== undefined) {
      this.modelService.newModelManifest(this.selectedType, name)
        .subscribe(
          mf => {
            this.addingManifest = false;
            this.selectedManifest = mf;
            this.refreshManifests();
          },
          (e: ErrorResponse) => this.logger.userErrorMessage(e.formatMessage("Model could not be saved")));
    }
    else { this.logger.userMessage("Please enter a value"); }
  }
  deleteManifest() {
    this.modelService.deleteModelManifest(this.selectedManifest)
        .subscribe(r => {
          this.logger.userSuccessMessage(("Model '" + this.selectedManifest.name + "' successfully deleted"));
          this.refreshManifests();
        });
  }
  
  onSelectVersion(version: ModelVersion) {
    this.selectedVersion = version;
    
    this.modelService.getModelByTypeNameVersion(this.selectedType, this.selectedManifest, this.selectedVersion)
      .then(m => this.model = m);
  }
}

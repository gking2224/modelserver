import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

import { Model } from '../../core/index';

import { ToggleDirective } from '../toggle/index';

@Component({
  moduleId: module.id
  , selector: 'ms-modelsummary'
  , templateUrl: 'model-summary.template.html'
  , styleUrls: ['model-summary.scss']
  , directives: [ToggleDirective]
})
export class ModelSummaryComponent implements OnInit {
  @Input() model : Model;
  @Input() showButtons : boolean = false;
  @Input() showScript : boolean = false;
  
  @Output() edit = new EventEmitter();
  @Output() execute = new EventEmitter();
  @Output() delete = new EventEmitter();
  @Output() disable = new EventEmitter();
  @Output() enable = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }
  
  onEdit() {
    this.edit.emit(null);
  }
  
  onExecute() {
    this.execute.emit(null);
  }
  
  onDisable() {
    this.disable.emit(null);
    }
  
  onDelete() {
    this.delete.emit(null);
  }
  
  onEnable() {
    this.enable.emit(null);
  }

}

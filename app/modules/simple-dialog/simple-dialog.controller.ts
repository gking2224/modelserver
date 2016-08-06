import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

import { LoggingService } from '../../core/index';

import {DraggableDirective, AutoSuggestDirective} from '../directives';

@Component({
  moduleId: module.id
  , selector: 'ms-simple-dialog',
  templateUrl: 'simple-dialog.template.html',
  styleUrls: ['simple-dialog.scss'],
  directives: [DraggableDirective, AutoSuggestDirective]
})
export class SimpleDialogComponent implements OnInit {
  
  @Output()
  cancel = new EventEmitter();

  @Output()
  save = new EventEmitter<string>();

  @Input() title : string;
  @Input() label : string;
  @Input() placeholder : string;
  @Input() value : string;
  @Input() autoSuggestType : string;

  constructor(private logger : LoggingService) {
  }
  ngOnInit() {
  }
  onCancel() { this.cancel.emit(null); }
  
  onSave() {
    this.save.emit(this.value);
  }
}

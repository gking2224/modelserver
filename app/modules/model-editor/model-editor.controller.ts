import {Component, ElementRef, Input, OnInit, Output, EventEmitter} from '@angular/core';
import {EditorDirective} from './model-editor.directive';
import { LoggingService } from '../../core/index';

@Component({
    moduleId: module.id
    , selector: "ms-editor",
    template: `
    <textarea style="display:block" (update)="update.emit($event)" editor id="{{id}}" name="code">{{scriptValue}}</textarea>
`,
  directives: [EditorDirective],
  styleUrls: ['model-editor.scss']
})


export class ModelEditorComponent implements OnInit {

  @Input() scriptValue : string;
  @Input() id : string;
  @Output() update = new EventEmitter<string>();

  constructor(private logger : LoggingService) { }
  ngOnInit() {
  }
}

import {Directive, Renderer, ElementRef, Output, EventEmitter } from '@angular/core';
import { LoggingService } from '../../core/index';

declare var CodeMirror: any;

@Directive({
  selector: '[editor]'
})
export class EditorDirective {
  editor: any;
  
  @Output() update = new EventEmitter();

  constructor(private element: ElementRef, private renderer: Renderer, private logger: LoggingService) {
  }

  ngAfterViewInit() {
    this.editor = CodeMirror.fromTextArea(
      this.element.nativeElement,
      {
        lineNumbers: true,
        mode: 'groovy',
        globalVars: true,
        lineWrapping: true,
        value: this.element.nativeElement.innerHTML
      }
    );
    this.editor.on('change', (editor: any) => {
        this.update.emit(editor.getDoc().getValue());
    });
  }
}

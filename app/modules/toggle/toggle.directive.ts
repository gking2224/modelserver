import {Directive, Renderer, ElementRef, Output, EventEmitter, Input } from '@angular/core';
import { LoggingService } from '../../core/index';

@Directive({
  selector: "[msToggle]"
})
export class ToggleDirective {
  
  @Output() onshow = new EventEmitter();
  @Output() onhide = new EventEmitter();
  @Input("msToggle") target : string;
  @Input("msToggleStartHidden") hidden : boolean = false;

  toggleEl : any;
  targetEls : any[];
  originalDisplays : string[];
  
  constructor(private element: ElementRef, private renderer: Renderer, private logger : LoggingService) { }

  ngAfterViewInit() {
    // set style of handle
    this.toggleEl = this.element.nativeElement;
    this.toggleEl.style.cursor = 'pointer';

    // add click handler to handle
    this.toggleEl.onclick = () => this.handleClicked();

    // get target(s), capture initial display style and set initial state
    this.targetEls = this.getTargets(this.toggleEl);
    console.log(this.targetEls);
    this.captureOriginalDisplays(this.targetEls); // .style.display;
    this.setTargetsState(this.targetEls);
  }

  getTargets(el : any) {
    if (this.target == null || this.target === "") {
      return [el.nextElementSibling];
    }
    else {
        let rv = el.parentElement.querySelectorAll(this.target);
        return rv;
    }
  }

  captureOriginalDisplays(els : any[]) {
    this.originalDisplays = [];
    for (let el of els) {
      let d = el.style.display;
      if (d === 'none') { d = 'inherit'; }// if hidden at start, we don't know what display style should be
      this.originalDisplays.push(d);
    }
  } 

  setTargetsState(els: any[]) {
    let idx = 0;
    for (let el of els) {
      if (el.style == null) { el.style = {}; }
      el.style.display = (this.hidden) ? "none" : this.originalDisplays[idx++];
    }
  }

  handleClicked() {
    this.hidden = !this.hidden;
    this.setTargetsState(this.targetEls);
    if (!this.hidden) { this.onshow.emit(null); }
    else { this.onhide.emit(null); }
  }
}

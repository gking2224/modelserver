import {Directive, Renderer, ElementRef, Output, EventEmitter, Input } from '@angular/core';
import { LoggingService } from '../../core/index';

declare var d3: any;

@Directive({
  selector: '[msDraggable]'
})
export class DraggableDirective {
  
  handle : any;
  d3handle : any;
  handleActive : boolean = false;
  
  @Input("msDraggable") subject : string;
  constructor(private element: ElementRef, private renderer: Renderer, private logger : LoggingService) {
  }

  ngAfterViewInit() {
    this.handle = this.element.nativeElement;
    this.d3handle = d3.select(this.handle);
    this.d3handle.style("cursor", "move");

    // make sure we know when the handle is active, to prevent the whole draggable area acting as the handle
    this.registerHandleMouseEvents();

    let el = document.querySelector(this.subject);
    let d3el = d3.select(el);
    
    d3el.call(d3.drag().subject(el).container(document.querySelector("body"))
      .on("start", () => this.dragStarted())
      .on("end", () => this.dragFinished())
      .on("drag", () => this.dragged())
        // only initiate drag event if mouse down recorded on the handle
      .filter( () => this.handleActive ));
  }
    
  dragStarted() {
    d3.select(d3.event.subject).classed("dragging", true);
  }
    
  dragFinished() {
    d3.select(d3.event.subject).classed("dragging", false);
    this.handleActive = false;
  }
  // move drag subject
  dragged() {
    let el =  d3.select(d3.event.subject);
    let left: number = Number(el.style("left").replace("px", ""));
    let top: number = Number(el.style("top").replace("px", ""));
    let tx: number = left + d3.event.dx;
    let ty: number = top + d3.event.dy;
    el.style("left", tx + "px");
    el.style("top", ty + "px");
  }

  // record when we do mouse down on handle
  registerHandleMouseEvents() {
    this.d3handle.on("mousedown", () => this.handleActive = true);
    // onmouseup not triggered due to dragFinished event above
  }
}

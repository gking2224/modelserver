import {Directive, Renderer, ElementRef, Output, EventEmitter, Input } from '@angular/core';
import { LoggingService, Keys } from '../../core/index';
import { AutoSuggestService } from './auto-suggest.service';

declare var d3: any;

@Directive({
  selector: '[msAutoSuggest]'
})
export class AutoSuggestDirective {
    
  @Input("msAutoSuggest") suggestionType : string;
  @Output() save = new EventEmitter();
  @Output() cancel = new EventEmitter();
  inputValue: string; // input field value
  @Output() valueChange = new EventEmitter();
  
  el : any; // native element (input)
  parentEl : any; // parent node
  d3el: any; // d3 selection of input
  d3parentEl: any;
  suggestionsDiv: any; // ctr for suggestionElements (div)
  currentSuggestion: number = -1;
  suggestionElements: any[] = []; // list of suggestion d3 elements (p)
  suggestionsActive : boolean = false; // prevent blur of input field hiding suggestions when mouseover
  constructor(
    private element: ElementRef
    , private renderer: Renderer
    , private logger : LoggingService 
    , private service : AutoSuggestService
  ) {
    
  }
  
  @Input() set value(value: string) {
    this.inputValue = value;
  }

  ngAfterViewInit() {
    this.el = this.element.nativeElement;
    this.parentEl = this.el.parentNode;
    this.d3el = d3.select(this.el);
    this.d3parentEl = d3.select(this.parentEl);
    
    // input field behaviour
    this.d3el.on("keyup", () => this.keyEvent());
    this.d3el.on("blur", () => {
      if (!this.suggestionsActive) {
        this.clear();
      }
    });
  }
  
  // handle key events on the input box
  keyEvent() {
    let code = d3.event.keyCode;
    // go up/down
    if (code === Keys.UP) { this.upSelection(); }
    else if (code === Keys.DOWN) { this.downSelection(); }
    // escape out of suggestion
    else if (code === Keys.ESCAPE && this.currentSuggestion !== -1) {
      this.clear();
    }
    // send cancel event
    else if (code === Keys.ESCAPE && this.suggestionsDiv == null) {
      this.cancel.emit(null);
    }
    // enter pressed on suggestion
    else if (code === Keys.ENTER && this.currentSuggestion !== -1) { this.populateSuggestion(); }
    // send save event
    else if (code === Keys.ENTER && this.currentSuggestion === -1) {
      this.clear();
      this.save.emit(this.d3el.property("value"));
    }
    // other keys
    else { this.fetchSuggestions(); }
  }

  // fetch suggestions for current input field value
  fetchSuggestions() {
    this.clear();
    if (this.inputValue !== "") {
    
      // get suggestions from service
      let suggestions: string[] =
        this.service.getSuggestions(this.suggestionType, this.inputValue);
      
      // create container
      this.suggestionsDiv = d3.select(this.el.parentNode).append("div");
      this.suggestionsDiv.classed("autoSuggestCtr", true);
      
      this.positionSuggestionsCtr();
      
      // add suggestions to container
      for (let s of suggestions) {
        this.doAddSuggestion(s);
      }
    }
  }
  
  // add a single suggestion to the container
  doAddSuggestion(s: string) {
    let sEl = this.suggestionsDiv.append("p");
    sEl.text(s);
    let idx = this.suggestionElements.length;
    this.suggestionElements.push(sEl);

    sEl.style("height", this.d3el.style("height"));
    this.registerSuggestionMouseEvents(sEl, idx);
  }
  
  registerSuggestionMouseEvents(sEl : any, idx : number) {
    sEl
      .on("mouseup", () => {
        this.currentSuggestion = idx;
        console.log(this.currentSuggestion);
        this.populateSuggestion();
        this.suggestionsActive = false;
      })
      .on("mouseover", () => {
        this.unHighlightCurrent();
        this.currentSuggestion = idx;
        this.highlightCurrent();
        this.suggestionsActive = true;
      })
      .on("mouseout", () => {
        this.unHighlightCurrent();
        this.currentSuggestion = -1;
        this.suggestionsActive = false;
      });
  }
  
  positionSuggestionsCtr() {
    if (this.d3parentEl.style("display") !== "table-cell") {
      this.suggestionsDiv.style("left", this.d3el.property("offsetLeft") + "px");
    }
//    // DEBUGGING CODE
//    this.dumpPositionProps(this.el);
//    //input field's parent properties
//    console.log("input parent:");
//    this.dumpPositionProps(this.el.parentNode);
    
    this.suggestionsDiv.style("width", this.el.getBoundingClientRect().width + "px");
    
  }
  
//    // DEBUGGING FUNCTION
  dumpPositionProps(el : any) {
    console.log(el);
    console.log(el.getBoundingClientRect());
    let d3el = d3.select(el);
    console.log(d3el);
    console.log(d3el.property("offsetLeft") + " " + d3el.style("padding-left") + " " + d3el.style("margin-left"));
  }
  
  downSelection() {
    if ((this.currentSuggestion + 1) < this.suggestionElements.length) {
      this.unHighlightCurrent();
      this.currentSuggestion++;
      this.highlightCurrent();
      this.emitValue();
    }
  }
  
  upSelection() {
    if (this.currentSuggestion > -1) {
      this.unHighlightCurrent();
      this.currentSuggestion--;
      this.highlightCurrent();
      this.emitValue();
    }
  }
  
  populateSuggestion() {
    
    this.emitValue();
    this.clear();
  }
  
  unHighlightCurrent() {
    if (this.currentSuggestion !== -1) { this.suggestionElements[this.currentSuggestion].classed("highlighted", false); }
  }

  highlightCurrent() {
    if (this.currentSuggestion !== -1) { this.suggestionElements[this.currentSuggestion].classed("highlighted", true); }
  }
  
  currentSelectionValue() {
    if (this.currentSuggestion === -1) { return ""; }
    else { return this.suggestionElements[this.currentSuggestion].text(); }
  }
  
  clear() {
    this.suggestionElements = [];
    this.currentSuggestion = -1;
    if (this.suggestionsDiv != null) {
      this.suggestionsDiv.remove();
      this.suggestionsDiv = null;
    }
  }
  
  emitValue() {
    this.valueChange.emit(this.currentSelectionValue());
  }

}

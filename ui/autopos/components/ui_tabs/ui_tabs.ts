import {Component, Directive, Input, QueryList,
  ViewContainerRef, TemplateRef, ContentChildren} from "angular2/core";
import {Type} from "angular2/core";
import {Location} from "angular2/router";

@Directive({
  selector: "[ui-pane]"
})
export class UiPane {
  @Input() title:string;
  @Input() path:string;
  private _active:boolean = false;

  constructor(public viewContainer:ViewContainerRef,
              public templateRef:TemplateRef) {
  }

  @Input() set active(active:boolean) {
    if (active === this._active) return;
    this._active = active;
    if (active) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainer.remove(0);
    }
  }

  get active():boolean {
    return this._active;
  }
}

@Component({
  selector: "ui-tabs",
  template: `
    <ul class="nav nav-tabs">
      <li *ngFor="var pane of panes"
          (click)="select(pane)"
          role="presentation" [class.active]="pane.active">
        <a href="javascript: false">{{pane.title}}</a>
      </li>
    </ul>
    <ng-content></ng-content>
    `,
  provider: [Location]
})
export class UiTabs {

  constructor(private location:Location) {
  }

  @ContentChildren(<Type>UiPane, true) panes:QueryList<UiPane>;

  select(pane:UiPane) {
    this.panes.toArray()
      .forEach(p => {
        if (p === pane) {
          p.active = true;
          this.location.go(p.path);
          // using location instead of router because of error.
          // Cannot read property 'viewManager' of null
        } else {
          p.active = false;
        }
      });
  }
}

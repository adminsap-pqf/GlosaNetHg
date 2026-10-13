import { Component, OnInit, Renderer, ElementRef, Input, SimpleChanges } from '@angular/core';
import { SessionUser } from '../../../services/session/session.service';

/* Dev Tools */
import { DEBUG } from "../../../settings/app.settings";

@Component({
  selector: 'gn-download-history',
  templateUrl: './download-history.component.html',
  styleUrls: ['./download-history.component.scss']
})
export class DownloadHistoryComponent implements OnInit {

  @Input() items: any[];

  open: Boolean;
  clickListener: Function;

  constructor(private renderer: Renderer, private elementRef: ElementRef) {
    this.clickListener = renderer.listenGlobal(
      'document',
      'click',
      (event: MouseEvent) => this.handleGlobalClick(event)
    );
  }

  ngOnChanges(changes: SimpleChanges) {
    DEBUG && console.log(changes);
  }

  ngOnInit() {
    this.open = SessionUser.getInstance().getOpenDescargas();
    DEBUG && console.log(this.open);
    this.items = SessionUser.getInstance().getListDescargas();
  }

  close(): void {
    SessionUser.getInstance().getOpenDescargas().visible = false;
  }

  openClose(): void {
    SessionUser.getInstance().getOpenDescargas().visible = !SessionUser.getInstance().getOpenDescargas().visible;
    DEBUG && console.log(SessionUser.getInstance().getOpenDescargas());
    DEBUG && console.log(this.open);
  }

  /* Escucha el evento click globalmente */
  handleGlobalClick(event: MouseEvent): void {
    const withinElement = this.elementRef.nativeElement.contains(event.target);
    if (!this.elementRef.nativeElement.contains(event.target)) {
      this.close();
    }
  }
}

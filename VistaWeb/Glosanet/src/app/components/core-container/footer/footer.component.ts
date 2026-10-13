import { Component, OnInit } from '@angular/core';
import { SessionUser } from '../../../services/session/session.service';
import { APP_VERSION_NUMBER } from "../../../util/common.protocols";

@Component({
  selector: 'gn-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  user:any = '';
  today = new Date();
  permiso:any = '';
  appVersion:string = APP_VERSION_NUMBER;

  constructor() { }

  ngOnInit() {
    this.user = SessionUser.getInstance().getUsuario().usuario;
    this.permiso = SessionUser.getInstance().getUsuario().permiso;
  }

}

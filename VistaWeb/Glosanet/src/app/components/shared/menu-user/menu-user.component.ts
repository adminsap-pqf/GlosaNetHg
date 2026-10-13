import { Component, ElementRef, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, Renderer, SimpleChanges,
} from '@angular/core';
import { Router } from '@angular/router';
import { SessionUser } from '../../../services/session/session.service';
import { UserService } from '../../../services/user/user.service';
import { Subscription } from 'rxjs';
import { AuthenticationService } from '../../../services/authentication/authentication.service';


/* Dev Tools */
import { DEBUG } from '../../../settings/app.settings';

@Component({
  selector: 'gn-menu-user',
  templateUrl: './menu-user.component.html',
  styleUrls: ['./menu-user.component.scss']
})
export class MenuUserComponent implements OnInit, OnChanges, OnDestroy  {

  @Output() itemSelected: EventEmitter<any> = new EventEmitter<any>();
  @Input() items: any;
  @Input() user: any;

  clickListener: Function;
  open: boolean;
  classActive = new Array();
  numDataStage: String = '';
  ffinDataStage: String;
  finicioDataStage: String;
  urlImagen:String;

  subscription: Subscription;

  constructor(private router: Router,
              private renderer: Renderer,
              private elementRef: ElementRef,
              private userService: UserService,
              private authService: AuthenticationService) {
    this.clickListener = renderer.listenGlobal(
      'document',
      'click',
      (event: MouseEvent) => this.handleGlobalClick(event)
    );
   }

  ngOnInit() {
    this.open = false;

    this.subscription = this.userService.message.subscribe(
      (data) => {
        DEBUG && console.log('Subscription');
        if(this.user.empresas[0] !== undefined){
          if(data.idEmp == this.user.empresas[0].idEmpresa && data.idCli == SessionUser.getInstance().getCliente().idCliente){
            this.getImagenEmpresa();
          }
        }
      }
    );
  }

  ngOnChanges(changes: SimpleChanges) {
    if (this.items !== undefined) {
      this.classActive.fill(false);
    }
    if (this.user.empresas != undefined){
      this.numDataStage = this.user.empresas[0].numDataStage;
      this.ffinDataStage = this.user.empresas[0].ffinDataStage;
      this.finicioDataStage = this.user.empresas[0].finicioDataStage;
      //this.urlImagen = this.user.empresas[0].imagenEmpresa;
      this.getImagenEmpresa();

    }
  }

  /* Escucha el evento click globalmente */
  handleGlobalClick(event: MouseEvent): void {
    const withinElement = this.elementRef.nativeElement.contains(event.target);
    if (!this.elementRef.nativeElement.contains(event.target)) {
      this.close();
    }
  }
  close(): void {
    this.open = false;
  }

  reset() {
    this.classActive.fill(false);
  }

  select(item: any, i: any ): void {
    DEBUG && console.log(item);
    DEBUG && console.log(i);
    this.reset();
    this.classActive[i] = true;
    this.itemSelected.emit(i);
    this.close();
  }

  ngOnDestroy() {
    this.clickListener();
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  closeSession() {
    this.authService.logout();
    this.router.navigate(['']);
    location.reload();
  }

  irConfiguraciones(){
    this.close();
    this.router.navigate(["protected/configuraciones"]);
  }

  getImagenEmpresa(){
    this.userService.downloadLogo(this.user.empresas[0].idEmpresa, SessionUser.getInstance().getCliente().idCliente)
      .subscribe(
        data => {
          if (data) {
            this.urlImagen = 'data:image/png;base64,' + data;
          } else {
            this.urlImagen = require('../../../../assets/images/enterprise.svg');
          }
        },
        error => {
          DEBUG && console.log(error);
          this.urlImagen = require('../../../../assets/images/enterprise.svg');
        });
  }

}

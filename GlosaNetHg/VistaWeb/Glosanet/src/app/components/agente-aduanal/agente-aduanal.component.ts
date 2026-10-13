import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AgenteAduanalHttpService } from '../../services/agente-aduanal/agente-aduanalHttp.service';
import { Parametros } from '../../model/parametros.class';
import { SessionUser } from '../../services/session/session.service';
import { AgenteAduanal } from '../../model/agenteAduanal.class';
import { AgenciaAduanal } from '../../model/agenciaAduanal.class';
import { DireccionFiscal } from '../../model/direccionFiscal.class';

/* Dev Tools */
import { DEBUG } from "../../settings/app.settings";

@Component({
  selector: 'gn-agente-aduanal',
  templateUrl: './agente-aduanal.component.html',
  styleUrls: ['./agente-aduanal.component.scss']
})
export class AgenteAduanalComponent implements OnInit {

  classAnimation = 'w3-animate-zoom-out';
  dForm: FormGroup;
  fForm: FormGroup;
  resetTabs: boolean = false;
  lstItems: string [] = ['Datos Generales', 'Dirección Fiscal'];
  visibleCampos: Boolean = true;
  pWizard: any = 0;
  pWizard2: any = 0;

  tabs: boolean = false;    /* false = Actua como wizard | true = actua como tab */
  check: boolean = true;    /* false = se esconde la palomita | true = se muestra la palomita */

  classOpen: any = 'close';
  classAgente: any = 'content-agenteClose';

  rfcClass: any[] = [];

  arrayAgentes: any[] = [];
  arrayRfc: any[] = [];
  itemAgentes: any[] = [];

  nombre: any = '';
  apellido:any = '';
  rfc: any = '';
  telefono: any = '';
  correo  : any = '';
  calle: any = '';
  noExt: any = '';
  noInt: any = '';
  delCol: any = '';
  estado: any = '';
  pais: any = '';
  codigo:any;

  classOpenRfc:any[] = [];

  agente: AgenteAduanal;
  agencia: Array<AgenciaAduanal>;

  idAgente:any = 0;
  numeroPatente:any = '';
  idDireccion:any = 0;
  mun:any = '';
  idAgenciaAduanal:any = 0;

  typePopUp:number = 0;

  countValid: any = 0;
  countValidAux: any[] = new Array(5).fill(false);

  datosGeneralesValid:boolean = false;

  countValid2: any = 0;
  countValidAux2: any[] = new Array(7).fill(false);

  rfcValid:boolean = false;

  itemsDropList:any[] = [{nombre: 'Patente'},{nombre: 'Nombre'}];

  constructor(private agentes: AgenteAduanalHttpService) { }

  ngOnInit() {
    this.dForm = new FormGroup({
      nombre: new FormControl('', [Validators.required, Validators.pattern('^([a-zñáéíóú, A-ZÁÉÍÓÚ]{1}[a-zñáéíóú,A-ZÁÉÍÓÚ]+[\s]*)+$')]),
      apellidos: new FormControl('', [Validators.required, Validators.pattern('^([a-zñáéíóú, A-ZÁÉÍÓÚ]{1}[a-zñáéíóú,A-ZÁÉÍÓÚ]+[\s]*)+$')]),
      rfc: new FormControl('', [Validators.required, Validators.pattern('^([A-ZÑ\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1]))([A-Z\\d]{3})?$')]),
      telefono: new FormControl('', [Validators.required, Validators.pattern('^(([0-9])+(-){0,1})+$')]),
      correo: new FormControl('', [Validators.required, Validators.pattern('^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$')])
    });
    this.fForm = new FormGroup({
      calle: new FormControl('', [Validators.required, Validators.pattern('^([a-zñáéíóú, A-ZÁÉÍÓÚ, 0-9]{1}[a-zñáéíóú,A-ZÁÉÍÓÚ, 0-9]+[\s]*)+$')]),
      noExt: new FormControl('', [Validators.required, Validators.pattern('^([a-zñáéíóú,A-ZÁÉÍÓÚ, 0-9]+[\s]*)+$')]),
      col: new FormControl('', [Validators.required]),
      noInt: new FormControl('', [Validators.required, Validators.pattern('^([a-zñáéíóú,A-ZÁÉÍÓÚ, 0-9]+[\s]*)+$')]),
      estado: new FormControl('', [Validators.required]),
      pais: new FormControl('', [Validators.required, Validators.pattern('^([a-zñáéíóú, A-ZÁÉÍÓÚ]{1}[a-zñáéíóú,A-ZÁÉÍÓÚ]+[\s]*)+$')]),
      cp: new FormControl('', [Validators.required, Validators.pattern('^[0-9]{5}$')])
    });



    this.dForm.valueChanges.subscribe(
      () => {
        this.countValid = 0;
        this.countValidAux.fill(false);
        this.countValidAux[0] = this.dForm.get('nombre').valid;
        this.countValidAux[1] = this.dForm.get('apellidos').valid;
        this.countValidAux[2] = this.dForm.get('rfc').valid;
        this.countValidAux[3] = this.dForm.get('telefono').valid;
        this.countValidAux[4] = this.dForm.get('correo').valid;

        for (let i of this.countValidAux) {
          if (i) {
            this.countValid++;
          }
          if (this.countValid == 5) {
            DEBUG && console.log("Valido");
            this.datosGeneralesValid = true;
          } else {
            this.datosGeneralesValid = false;
          }
        }
      });

      this.fForm.valueChanges.subscribe(
        () => {
          this.countValid2 = 0;
          this.countValidAux2.fill(false);
          this.countValidAux2[0] = this.fForm.get('calle').valid;
          this.countValidAux2[1] = this.fForm.get('noExt').valid;
          this.countValidAux2[2] = this.fForm.get('col').valid;
          this.countValidAux2[3] = this.fForm.get('noInt').valid;
          this.countValidAux2[4] = this.fForm.get('estado').valid;
          this.countValidAux2[5] = this.fForm.get('pais').valid;
          this.countValidAux2[6] = this.fForm.get('cp').valid;

          for (let i of this.countValidAux2) {
            if (i) {
              this.countValid2++;
            }
            if (this.countValid2 == 7) {
              DEBUG && console.log("Valido");
              this.rfcValid = true;
            } else {
              this.rfcValid = false;
            }
          }
        });

    this.obtenerAgentesYAgencias();
  }

  ngOnChange() {}
  ngDoCheck() {}

  agregarDatos($event, type:number, item?:any, ) {
    $event.stopPropagation();
    DEBUG && console.log(item);
    this.resetTabs = false;
    this.visibleCampos = true;
    this.classAnimation = 'w3-animate-zoom';
    this.typePopUp = type;
    switch (type) {
      case 1:
        this.tabs = false;
        this.check = true;
        break;

      case 2:
        this.tabs = true;
        this.check = false;
        this.dForm.patchValue({
          nombre: this.nombre,
          apellidos: this.apellido,
          rfc: this.rfc,
          telefono: this.telefono,
          correo: this.correo,
        });

        this.fForm.patchValue({
          calle: this.calle,
          noExt: this.noExt,
          col: this.delCol,
          noInt: this.noInt,
          estado: this.estado,
          pais: this.pais,
          cp: this.codigo
        });
        break;

      case 3:
        this.tabs = false;
        this.check = true;
        break;

      case 4:
        this.tabs = true;
        this.check = false;
        this.dForm.patchValue({
          nombre: item.rfc,
          apellidos: item.razonSocial,
          rfc: item.nombre,
          telefono: item.telefono,
          correo: item.correoElectronico,
        });

        this.fForm.patchValue({
          calle: item.direccion.calle,
          noExt: item.direccion.noExterior,
          col: item.direccion.colonia,
          noInt: item.direccion.noInterior,
          estado: item.direccion.estado,
          pais: item.direccion.pais,
          cp: item.direccion.codigoPostal
        });

        this.idDireccion = item.direccion.idDireccion;
        this.mun = item.direccion.municipio;
        this.idAgenciaAduanal = item.idAgenciaAduanal;
        break;

      default:
        break;
    }
  }

  closeModal() {
    this.resetTabs = true;
    this.classAnimation = 'w3-animate-zoom-out';
    this.pWizard = 0;
    this.pWizard2 = 0;
    this.resetForms();
  }

  resetForms() {
    this.dForm.patchValue({
      nombre: '',
      apellidos: '',
      rfc: '',
      telefono: '',
      correo: '',
    });

    this.fForm.patchValue({
      calle: '',
      noExt: '',
      col: '',
      noInt: '',
      estado: '',
      pais: '',
      cp: ''
    });
  }

  back() {
    if (this.pWizard > 0) {
      this.pWizard--;
      this.pWizard2--;
    }
    DEBUG && console.log(this.pWizard);
  }

  next() {
    if (this.pWizard < (this.lstItems.length - 1)) {
      this.pWizard++;
      this.pWizard2++;
    }
    DEBUG && console.log(this.pWizard);
  }

  tab($event) {
    DEBUG && console.log($event);
    this.pWizard2 = $event;
  }

  open() {
    if (this.classOpen == 'close') {
      this.classOpen = 'open';
      this.classAgente = 'content-agente';
    } else {
      this.classOpen = 'close';
      this.classAgente = 'content-agenteClose';
    }
  }

  openRfc(index:any){
    if (this.classOpenRfc[index] == 'close') {
      this.classOpenRfc[index] = 'open';
      this.rfcClass[index] = 'content-rfc';
    } else {
      this.classOpenRfc[index] = 'close';
      this.rfcClass[index] = 'content-rfcClose';
    }
  }

  itemSelected(agente: any, index: any){
    DEBUG && console.log(agente);
    this.itemAgentes.fill('itemsDiv');
    this.itemAgentes[index] = 'itemsDivSelected';
    this.idAgente = agente.idAgente !== undefined ? agente.idAgente : 0 ;
    this.numeroPatente = agente.numeroPatente;
    this.idDireccion = agente.direccion !== undefined || agente.direccion !== '' ? agente.direccion.idDireccion : 0;
    this.mun = agente.direccion !== undefined ? agente.direccion.municipio : '';
    agente.nombre !== '' ? this.fillDatosGenerales(agente) : this.resetDatosGenerales();
    this.arrayRfc = agente.agencias;
    this.rfcClass = new Array(this.arrayRfc.length).fill('content-rfcClose');
    this.classOpenRfc = new Array(this.arrayRfc.length).fill('close');
    if(agente.nombre !== ''){
      this.classOpen = 'open';
      this.classAgente = 'content-agente';
    }else{
      this.classOpen = 'close';
      this.classAgente = 'content-agenteClose';
    }
  }

  fillDatosGenerales(item: any){
    this.nombre = item.nombre;
    this.apellido = item.apellido;
    this.rfc = item.rfc;
    this.telefono = item.telefono;
    this.correo = item.correoElectronico;
    this.calle = item.direccion.calle;
    this.noExt = item.direccion.noExterior;
    this.noInt = item.direccion.noInterior !== '' ? item.direccion.noInterior : 'S/N' ;
    this.delCol = item.direccion.colonia;
    this.estado = item.direccion.estado;
    this.pais = item.direccion.pais;
    this.codigo = item.direccion.codigoPostal;
  }

  resetDatosGenerales(){
    this.nombre = "";
    this.rfc = "";
    this.telefono = "";
    this.correo = "";
    this.calle = "";
    this.noExt = "";
    this.noInt = "";
    this.delCol = "";
    this.estado = "";
    this.pais ="";
  }

  guardar(datosGenerales:any, rfc:any) {
    SessionUser.getInstance().setLoading(true);
    this.pWizard = 0;
    this.pWizard2 = 0;
    this.resetForms();
    DEBUG && console.log(datosGenerales);
    DEBUG && console.log(rfc);
    DEBUG && console.log(this.idDireccion);

    let direccion = new DireccionFiscal();
    direccion.idDireccion = this.idDireccion !== undefined ? this.idDireccion : 0;
    direccion.calle = rfc.calle;
    direccion.noExterior = rfc.noExt;
    direccion.colonia = rfc.col;
    direccion.noInterior = rfc.noInt;
    direccion.estado = rfc.estado;
    direccion.pais = rfc.pais;
    direccion.codigoPostal = rfc.cp;
    direccion.municipio = this.mun;

    let agente = new AgenteAduanal();
    agente.nombre = datosGenerales.nombre;
    agente.apellido = datosGenerales.apellidos;
    agente.rfc = datosGenerales.rfc;
    agente.telefono = datosGenerales.telefono;
    agente.correoElectronico = datosGenerales.correo;
    agente.idAgente = this.idAgente;
    agente.numeroPatente = this.numeroPatente;
    agente.idCliente = SessionUser.getInstance().getIdClient();
    agente.direccion = direccion;

    let agencia = new AgenciaAduanal();
    agencia.agente = agente;
    agencia.correoElectronico = datosGenerales.correo;
    agencia.direccion = direccion;
    agencia.nombre = datosGenerales.nombre;
    agencia.razonSocial = datosGenerales.apellidos;
    agencia.rfc = datosGenerales.rfc;
    agencia.telefono = datosGenerales.telefono;

    switch (this.typePopUp) {
      case 1:
        this.agentes.actualizarAgenteAduanal(agente)
          .subscribe(
            data => {
              DEBUG && console.log(data);
              this.obtenerAgentesYAgencias();
            },
            error => {
              DEBUG && console.log('@guardar: failed -> option_1 ->', error);
              SessionUser.getInstance().setLoading(false);
              SessionUser.getInstance().setMessageFailed('al guardar');
              document.getElementById('requestFailed').style.display = 'block';
            });
        break;

      case 2:
        this.agentes.actualizarAgenteAduanal(agente)
          .subscribe(
            data => {
              DEBUG && console.log(data);
              this.obtenerAgentesYAgencias();
            },
            error => {
              DEBUG && console.log('@guardar: failed -> option_2 ->', error);
              SessionUser.getInstance().setLoading(false);
              SessionUser.getInstance().setMessageFailed('al guardar');
              document.getElementById('requestFailed').style.display = 'block';
            });
        break;

      case 3:
        agencia.direccion.municipio = '';
        this.agentes.agregarAgenciaAduanal(agencia)
          .subscribe(
            data => {
              DEBUG && console.log(data);
              this.obtenerAgentesYAgencias();
            },
            error => {
              DEBUG && console.log('@guardar: failed -> option_3 ->', error);
              SessionUser.getInstance().setLoading(false);
              SessionUser.getInstance().setMessageFailed('al guardar');
              document.getElementById('requestFailed').style.display = 'block';
            });
        break;

      case 4:
        agencia.idAgenciaAduanal = this.idAgenciaAduanal;
        this.agentes.actualizarAgenciaAduanal(agencia)
          .subscribe(
            data => {
              DEBUG && console.log(data);
              this.obtenerAgentesYAgencias();
            },
            error => {
              DEBUG && console.log('@guardar: failed -> option_4 ->', error);
              SessionUser.getInstance().setLoading(false);
              SessionUser.getInstance().setMessageFailed('al guardar');
              document.getElementById('requestFailed').style.display = 'block';
            });
        break;

      default:
        SessionUser.getInstance().setLoading(false);
        break;
    }
  }

  /* droplist */
  dropList(index, $event) {
    DEBUG && console.log($event);
    if ($event.nombre === 'Patente') {
      this.arrayAgentes = this.arrayAgentes.sort((a, b) => (a.numeroPatente < b.numeroPatente) ? -1 : 1);
      DEBUG && console.log(this.arrayAgentes);
    }

    if ($event.nombre === 'Nombre') {
      this.arrayAgentes = this.arrayAgentes.sort((a, b) => (a.nombre < b.nombre) ? -1 : 1);
      DEBUG && console.log(this.arrayAgentes);
    }

    this.classOpen = 'close';
    this.classAgente = 'content-agenteClose';
    this.classOpenRfc.fill('close');
    this.rfcClass.fill('content-rfcClose');
    if(this.arrayAgentes.length > 0 && this.arrayAgentes !== undefined){
      this.itemSelected(this.arrayAgentes[0], 0);
    }
  }

  /* servicios */
  obtenerAgentesYAgencias() {
    SessionUser.getInstance().setLoading(true);
    this.arrayAgentes = [];
    let parametros = new Parametros();
    parametros.id = SessionUser.getInstance().getIdClient();
    this.agentes.obtenerAgentesYAgencias(parametros)
      .subscribe(
      data => {
        DEBUG && console.log(data);
        for (let item of data) {
          let agentes = {
            agencias: item.agencias !== undefined ? item.agencias : '',
            apellido: item.apellido !== undefined ? item.apellido : '',
            correoElectronico: item.correoElectronico !== undefined ? item.correoElectronico : '',
            direccion: item.direccion !== undefined ? item.direccion : '',
            idAgente: item.idAgente !== undefined ? item.idAgente : '',
            idCliente: item.idCliente !== undefined ? item.idCliente : '',
            nombre: item.nombre !== undefined ? item.nombre : '',
            numeroPatente: item.numeroPatente !== undefined ? item.numeroPatente : '',
            rfc: item.rfc !== undefined ? item.rfc : '',
            telefono: item.telefono !== undefined ? item.telefono : ''
          };
          this.arrayAgentes.push(agentes);
        }
        this.arrayAgentes = this.arrayAgentes.sort((a, b) => (a.numeroPatente < b.numeroPatente) ? -1 : 1);
        this.itemAgentes = new Array(this.arrayAgentes.length).fill('itemsDiv');
        if (this.arrayAgentes.length > 0) {
          this.itemSelected(this.arrayAgentes[0], 0);
        }
        SessionUser.getInstance().setLoading(false);
      },
      error => {
        DEBUG && console.log('@obtenerAgentesYAgencias: failed', error);
        SessionUser.getInstance().setLoading(false);
        SessionUser.getInstance().setMessageFailed('al obtener las patentes');
        document.getElementById('requestFailed').style.display = 'block';
      });
    }
}

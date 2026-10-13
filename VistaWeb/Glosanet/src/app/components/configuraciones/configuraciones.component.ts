import {Component, OnInit} from '@angular/core';
import {SessionUser} from '../../services/session/session.service';
import {Usuario} from '../../model/usuario.class';
import {Empresa} from '../../model/empresa.class';
import {Cliente} from '../../model/cliente.class';
import {Router} from '@angular/router';
import {UserService} from '../../services/user/user.service';
import {Parametros} from '../../model/parametros.class';
import {ClienteService} from '../../services/cliente/cliente.service';
import {EmpresaService} from '../../services/empresa/empresa.service';
import {Respuesta} from '../../model/respuesta.class';
/* Dev Tools */
import {DEBUG} from '../../settings/app.settings';
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {ROUTE_FILES} from "../../util/common.protocols";
import * as _ from 'lodash';

@Component({
  selector: 'gn-configuraciones',
  templateUrl: './configuraciones.component.html',
  styleUrls: ['./configuraciones.component.scss']
})
export class ConfiguracionesComponent implements OnInit {

  user = new Usuario();  // Objeto porincipal de usuario
  userOriginal = new Usuario();
  cliente = new Cliente(); // Cliente seleccionado
  clienteOriginal = new Cliente();
  administrador = new Array<Usuario>(); // Lista de usuarios para editar sus permisos
  empresas = new Array<Empresa>(); // Lista de empresas del cliente
  empresasOriginal = new Array<Empresa>();

  file: FileList = null;
  file2: FileList = null;

  open = new Array<boolean>(3).fill(false);

  editDG: boolean = false;
  editE: boolean = false;

  date: Date;

  check = [];
  listPermisos = [];
  listPermisosOriginal: any;

  editUser: boolean = false;
  editD: boolean = false;

  classAnimation = 'w3-animate-zoom-out';

  tooltipV: any[] = [];

  password: string = '';
  passwordConfirm: string = '';

  rfcModal: string = '';
  razonSocialModal: string = '';
  imgEmpresaModal: string = '';
  rutaGlosaDocs: string = ROUTE_FILES;
  arraylogos: any[];

  public lstUsoCFDI: any[] = [
    {nombre: 'G01 - Adquisición de mercancias'},
    {nombre: 'G02 - Devoluciones, descuentos o bonificaciones'},
    {nombre: 'G03 - Gastos en general'},
    {nombre: 'I01 - Construcciones'},
    {nombre: 'I02 - Mobilario y equipo de oficina por inversiones'},
    {nombre: 'I03 - Equipo de transporte'},
    {nombre: 'I04 - Equipo de computo y accesorios'},
    {nombre: 'I05 - Dados, troqueles, moldes, matrices y herramental'},
    {nombre: 'I06 - Comunicaciones telefónicas'},
    {nombre: 'I07 - Comunicaciones satelitales'},
    {nombre: 'I08 - Otra maquinaria y equipo'},
    {nombre: 'D01 - Honorarios médicos, dentales y gastos hospitalarios'},
    {nombre: 'D02 - Gastos médicos por incapacidad o discapacidad'},
    {nombre: 'D03 - Gastos funerales'},
    {nombre: 'D04 - Donativos'},
    {nombre: 'D05 - Intereses reales efectivamente pagados por créditos hipotecarios (casa habitación)'},
    {nombre: 'D06 - Aportaciones voluntarias al SAR'},
    {nombre: 'D07 - Primas por seguros de gastos médicos'},
    {nombre: 'D08 - Gastos de transportación escolar obligatoria'},
    {nombre: 'D09 - Depósitos en cuentas para el ahorro, primas que tengan como base planes de pensiones'},
    {nombre: 'D10 - Pagos por servicios educativos (colegiaturas)'},
    {nombre: 'P01 - Por definir'}];

  selectedUsoCFDI: any = {nombre: 'P01 - Por definir'};

  public lstRegimenFiscal: any[] = [
    {nombre: '601 - General de Ley Personas Morales'},
    {nombre: '603 - Personas Morales con Fines no Lucrativos'},
    {nombre: '605 - Sueldos y Salarios e Ingresos Asimilados a Salarios'},
    {nombre: '606 - Arrendamiento'},
    {nombre: '608 - Demás ingresos'},
    {nombre: '609 - Consolidación'},
    {nombre: '610 - Residentes en el Extranjero sin Establecimiento Permanente en México'},
    {nombre: '611 - Ingresos por Dividendos (socios y accionistas)'},
    {nombre: '612 - Personas Físicas con Actividades Empresariales y Profesionales'},
    {nombre: '614 - Ingresos por intereses'},
    {nombre: '616 - Sin obligaciones fiscales'},
    {nombre: '620 - Sociedades Cooperativas de Producción que optan por diferir sus ingresos'},
    {nombre: '621 - Incorporación Fiscal'},
    {nombre: '622 - Actividades Agrícolas, Ganaderas, Silvícolas y Pesqueras'},
    {nombre: '623 - Opcional para Grupos de Sociedades'},
    {nombre: '624 - Coordinados'},
    {nombre: '628 - Hidrocarburos'},
    {nombre: '607 - Régimen de Enajenación o Adquisición de Bienes'},

    {nombre: '629 - De los Regímenes Fiscales Preferentes y de las Empresas Multinacionales'},
    {nombre: '630 - Enajenación de acciones en bolsa de valores'},
    {nombre: '615 - Régimen de los ingresos por obtención de premios'},
  ];

  selectedRegimenFiscal: any = {nombre: '601 - General de Ley Personas Morales'};

  constructor(private router: Router,
              private userService: UserService,
              private clienteService: ClienteService,
              private empresaService: EmpresaService,
              private authService: AuthenticationService) {
  }

  ngOnInit() {
    this.getUser();
    this.getClient();
    this.getUsuarioActualCompleto().then(() => {
      this.getAdminsitrador();
      this.getEmpresas();
    });

  }

  getUsuarioActualCompleto() {
    return new Promise((resolve, reject) => {
      let param = new Parametros();
      param.id = SessionUser.getInstance().getCliente().idCliente;
      this.userService.buscarUsuariosPorEmpresa(param)
        .subscribe(
          data => {
            DEBUG && console.log(data);

            /* TODO: Omitir referencia a memoria */
            this.listPermisosOriginal = JSON.parse(JSON.stringify(data));

            SessionUser.getInstance().setAdministrador(data);

            let list = [];
            _.forEach(data, user => {
              let permissions = _.orderBy(user.permisos, [obj => obj.permiso.idPermiso], 'asc');
              list.push(permissions);
            });

            this.listPermisos = [...list];
            DEBUG && console.log(this.listPermisos);
            resolve();
          },
          error => {
            DEBUG && console.log('@getUsuarioActualCompleto: ' + error);
            SessionUser.getInstance().setLoading(false);
            SessionUser.getInstance().setMessageFailed('al obtener permisos', 'Error');
            document.getElementById('requestFailed').style.display = 'block';
          });
    });
  }

  resetPermisosMatriz() {
    this.listPermisos = [];
    let listPermissions = [];
    _.forEach(this.listPermisosOriginal, (item, index) => {
      listPermissions.push([]);
      _.forEach(item.permisos, o => {
        listPermissions[index].push(o);
      });
      listPermissions[index] = _.orderBy(listPermissions[index], [obj => obj.permiso.idPermiso], 'asc');
    });
    this.listPermisos = listPermissions;
  }

  getUser() {
    this.user = Object.assign(new Usuario, SessionUser.getInstance().getUsuario());
    this.userOriginal = Object.assign(new Usuario, SessionUser.getInstance().getUsuario());
    DEBUG && console.log(this.user);
  }

  resetUser() {
    this.user = Object.assign(new Usuario, this.userOriginal);
  }

  getClient() {
    this.cliente = Object.assign(new Cliente, SessionUser.getInstance().getCliente());
    this.clienteOriginal = Object.assign(new Cliente, SessionUser.getInstance().getCliente());
    DEBUG && console.log(this.cliente);
    this.resetDroplistUsoCFDI();
    this.resetDropListRegimenFiscal();
  }

  resetClient() {
    DEBUG && console.log(this.clienteOriginal);
    this.cliente = Object.assign(new Cliente, this.clienteOriginal);

    this.resetDroplistUsoCFDI();
    this.resetDropListRegimenFiscal();
  }


  getEmpresas() {
    this.empresas = [];
    this.empresasOriginal = [];
    for (const item of SessionUser.getInstance().getCliente().empresas) {
      DEBUG && console.log(item.vigenciaMatriz);
      DEBUG && console.log(item.vigenciaMatrizFin);
      item.vigenciaMatriz !== undefined && item.vigenciaMatriz !== null ? item.vigenciaMatriz = new Date(item.vigenciaMatriz) : item.vigenciaMatriz = null;
      item.vigenciaMatrizFin !== undefined && item.vigenciaMatrizFin !== null ? item.vigenciaMatrizFin = new Date(item.vigenciaMatrizFin) : item.vigenciaMatrizFin = null;
      this.empresas.push(Object.assign(new Empresa, item));
      this.empresasOriginal.push(Object.assign(new Empresa, item));
    }
    DEBUG && console.log(this.empresas);

    this.arraylogos = new Array<any>(this.empresasOriginal.length);
    let i = 0;
    for (let item of this.empresas) {
      this.userService.downloadLogo(item.idEmpresa, SessionUser.getInstance().getCliente().idCliente)
        .subscribe(
          data => {
            if (data) {
              this.arraylogos[i] = 'data:image/png;base64,' + data;
            } else {
              this.arraylogos[i] = require('../../../assets/images/enterprise.svg');
            }
            i++;
          },
          error => {
            DEBUG && console.log(error);
            this.arraylogos[i] = require('../../../assets/images/enterprise.svg');
          });
    }
  }

  resetEmpresas() {
    this.empresas = [];
    for (let item of this.empresasOriginal) {
      this.empresas.push(Object.assign(new Empresa, item));
    }
  }

  getAdminsitrador() {
    this.administrador = [];
    for (let item of SessionUser.getInstance().getAdministrador()) {
      this.administrador.push(Object.assign(new Usuario, item));
    }
    DEBUG && console.log(this.administrador);
    this.tooltipV = new Array(this.administrador.length).fill(false);
    this.initMatrizPermisos();
  }

  resetDroplistUsoCFDI() {
    this.selectedUsoCFDI = {nombre: this.clienteOriginal.usoCFDI};
  }

  resetDropListRegimenFiscal() {
    this.selectedRegimenFiscal = {nombre: this.clienteOriginal.regimenFiscal};
  }


  /* ************************************************ Actualizar datos de usuario ************************************************ */

  updateUsuario() {
    SessionUser.getInstance().setLoading(true);
    let changePassword = false;
    if (this.passwordConfirm !== '') {
      changePassword = true;
      let sha1 = require('sha1');
      let hashedPassword = sha1(this.passwordConfirm);
      this.user.contrasenaNueva = hashedPassword;
    }
    DEBUG && console.log(this.user);
    this.userService.actualizarUsuarios(this.user)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          if (changePassword) {
            this.closeSession();
          } else {
            this.editD = false;
            this.refreshData();
          }
        },
        error => {
          DEBUG && console.log(error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al actualizar datos');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }

  updateCliente() {
    SessionUser.getInstance().setLoading(true);
    DEBUG && console.log(this.cliente);
    this.clienteService.actualizarCliente(this.cliente)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          this.editDG = false;
          this.refreshData();
        },
        error => {
          DEBUG && console.log(error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al actualizar datos');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }


  updateEmpresa() {
    SessionUser.getInstance().setLoading(true);
    let client = new Cliente();
    client.empresas = this.empresas;
    this.empresaService.actualizarVigencia(client)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          this.editE = false;
          this.refreshData();
        },
        error => {
          DEBUG && console.log(error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al actualizar datos');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }


  fileChange($event, rfc) {
    SessionUser.getInstance().setLoading(true);
    this.file = null;
    DEBUG && console.log($event);
    this.file = $event.target.files;
    this.empresaService.guardarMatriz(this.file, rfc, SessionUser.getInstance().getIdClient())
      .subscribe(
        data => {
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
        },
        error => {
          DEBUG && console.log(error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al cargar archivo');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }

  fileChange2($event, idEmpresa) { ////////////

    SessionUser.getInstance().setLoading(true);
    this.file2 = null;
    DEBUG && console.log($event);
    this.file2 = $event.target.files;
    this.empresaService.guardarImagenEmpresa(this.file2, idEmpresa, SessionUser.getInstance().getIdClient())
      .subscribe(
        data => {
          DEBUG && console.log(data);
          this.userService.refreshLogoEmpresa({idEmp: idEmpresa, idCli: SessionUser.getInstance().getIdClient()});
          this.refreshData();
        },
        error => {
          DEBUG && console.log(error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al cargar imagen');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }

  downloadMatriz(rfc) {
    SessionUser.getInstance().setLoading(true);
    this.empresaService.downloadMatriz(rfc, SessionUser.getInstance().getIdClient())
      .subscribe(
        data => {
          DEBUG && console.log('@downloadMatriz: success');
          SessionUser.getInstance().setLoading(false);
          if (data) {
            let element = document.createElement('a');
            element.setAttribute('href', "data:application/pdf;base64," + data);
            element.setAttribute('download', 'Matriz_' + rfc);
            element.setAttribute('target', '_blank');
            element.style.display = 'none';
            document.body.appendChild(element);
            element.click();
          } else {
            DEBUG && console.log('@downloadMatriz: archivo no encontrado');
            SessionUser.getInstance().setMessageFailed('al descargar el archivo');
            document.getElementById('requestFailed').style.display = 'block';
          }
        },
        error => {
          DEBUG && console.log('@downloadMatriz: failed', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al descargar el archivo');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }

  showPDF(rfc) {
    this.empresaService.downloadMatriz(rfc, SessionUser.getInstance().getIdClient())
      .subscribe(
        data => {
          let docContainer = document.querySelector('#preview');
          if (data) {
            docContainer.innerHTML = '<embed id="pdf" src="data:application/pdf;base64,' + data +
              '" width="450px" height="300px" alt="pdf" pluginspage="http://www.adobe.com/products/acrobat/readstep2.html">';
          } else {
            docContainer.innerHTML = '<p style="color: #C1272D; font-size: 16px; font-weight: 600">No se pudo cargar el documento PDF.</p>';
          }
        },
        error => {
          DEBUG && console.log(error);
          let docContainer = document.querySelector('#preview');
          docContainer.innerHTML = '<p style="color: #C1272D; font-size: 16px; font-weight: 600">No se pudo cargar el documento PDF.</p>';
        });
  }

  updatePermisos() {
    SessionUser.getInstance().setLoading(true);
    let i = 0;
    for (let item of this.administrador) {
      item.permisos = this.listPermisos[i];
      i++;
    }
    DEBUG && console.log(this.administrador);
    let resp = new Respuesta();
    resp.administrador = this.administrador;

    this.userService.actualizarPermisos(resp)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          this.editUser = false;

          /* TODO: Actualizar el arreglo original de permisos */
          this.listPermisosOriginal = [...this.listPermisos];

          this.refreshData();

        },
        error => {
          DEBUG && console.log(error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed();
          document.getElementById('requestFailed').style.display = 'block';
        });
  }

  openEmpresa() {
    this.open.fill(true);
  }

  closeEmpresa() {
    this.open.fill(false);
  }

  initMatrizPermisos() {
    this.check = [];
    for (let i in this.administrador) {
      this.check[i] = [];
      for (let j: number = 0; j < 11; j++) {
        this.check[i][j] = false;
      }
    }

    DEBUG && console.log(this.check);
  }

  openModal(rfc, razonSocial, img) {
    this.rfcModal = rfc;
    this.razonSocialModal = razonSocial;
    this.imgEmpresaModal = img;
    this.classAnimation = 'w3-animate-zoom';
    document.getElementById('id01').style.display = 'block';
    this.showPDF(rfc);
  }

  closeModal() {
    this.classAnimation = 'w3-animate-zoom-out';
    document.getElementById('id01').style.display = 'none';
  }

  irInicio() {
    this.router.navigate(["protected/datastagelibrary"]);
  }

  refreshData() {
    this.userService.obtenerUsuarioActual().subscribe(
      data => {
        DEBUG && console.log(data);
        SessionUser.getInstance().setNombreCompleto(data.usuario.nombre + " " + data.usuario.apellido);
        SessionUser.getInstance().setIdClient(data.cliente.idCliente);
        SessionUser.getInstance().setRfc(data.cliente.empresas[0].rfc);
        SessionUser.getInstance().setUsuario(data.usuario);
        SessionUser.getInstance().setCliente(data.cliente);
        SessionUser.getInstance().setidEmpresaActual(data.usuario.cliente.idEmpresaActual);
        SessionUser.getInstance().setNumDataStage(data.cliente.empresas[0].numDataStage);
        SessionUser.getInstance().setNumDataStageAux(data.cliente.empresas[0].numDataStage);

        this.getUser();
        this.getClient();
        this.getEmpresas();
        this.getAdminsitrador();

        /* TODO: Actualizamos localStorage */
        this.updateLocalStorage();

        SessionUser.getInstance().setLoading(false);
      },
      error => {
        DEBUG && console.log('error obtenerUsuarioActual: ' + error);
        SessionUser.getInstance().setLoading(false);
        SessionUser.getInstance().setMessageFailed();
        document.getElementById('requestFailed').style.display = 'block';
      });
  }

  closeSession() {
    this.authService.logout();
    this.router.navigate(['']);
    location.reload();
  }

  updateLocalStorage() {
    if (typeof (Storage) !== 'undefined') {
      let packedData = localStorage.getItem('currentUser');
      if (packedData) {
        packedData = JSON.parse(packedData);

        /* Datos de Usuario */
        _.update(packedData, 'userInfo.usuario.nombre', () => { return this.user.nombre; });
        _.update(packedData, 'userInfo.usuario.apellido', () => { return this.user.apellido; });
        _.update(packedData, 'userInfo.usuario.email', () => { return this.user.email; });

        /* Datos Generales */
        _.update(packedData, 'userInfo.cliente.representante', () => { return this.cliente.representante; });
        _.update(packedData, 'userInfo.cliente.telefono', () => { return this.cliente.telefono; });
        _.update(packedData, 'userInfo.cliente.razonSocial', () => { return this.cliente.razonSocial; });
        _.update(packedData, 'userInfo.cliente.regimenFiscal', () => { return this.cliente.regimenFiscal; });
        _.update(packedData, 'userInfo.cliente.rfc', () => { return this.cliente.rfc; });
        _.update(packedData, 'userInfo.cliente.usoCFDI', () => { return this.cliente.usoCFDI; });

        /* Empresas */
        _.forEach(this.empresas, (o, index) => {
          _.update(packedData, `userInfo.cliente.empresas.[${index}].vigenciaMatriz`, () => { return o.vigenciaMatriz; });
          _.update(packedData, `userInfo.cliente.empresas.[${index}].vigenciaMatrizFin`, () => { return o.vigenciaMatrizFin; });
        });

        localStorage.setItem('currentUser', JSON.stringify(packedData));
      }
    }
  }



}

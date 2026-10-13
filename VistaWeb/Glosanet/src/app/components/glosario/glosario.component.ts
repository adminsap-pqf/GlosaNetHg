import { Component, OnInit, Renderer2, ViewChild, ElementRef } from '@angular/core';
import { GlosarioService } from '../../services/glosario/glosario.service';
import { Parametros } from '../../model/parametros.class';
import { SessionUser } from '../../services/session/session.service';
import { Router } from '@angular/router';
import * as _ from 'lodash';

/* Dev Tools */
import { DEBUG } from '../../settings/app.settings';

@Component({
  selector: 'gn-glosario',
  templateUrl: './glosario.component.html',
  styleUrls: ['./glosario.component.scss']
})
export class GlosarioComponent implements OnInit {

  @ViewChild("searchresult", {static: false}) searchResult: ElementRef;

  lstTabs: String[] = ['Apéndices', 'Tratados Internacionales', 'Regiones'];
  lstApendice1: any[] = [{ nombre: 'Aduana', width: '15%', align: 'center' }, { nombre: 'Sección', width: '15%', align: 'center' }, { nombre: 'Denominación', width: '55%', align: 'left' }];
  lstApendice2: any[] = [{ nombre: 'Clave', width: '8%', align: 'center' }, { nombre: 'Descripción', width: '25%', align: 'left' }, { nombre: 'Supuestos de Aplicación', width: '60%', align: 'left' }];
  lstApendice3: any[] = [{ nombre: 'Clave', width: '15%', align: 'center' }, { nombre: 'Medios de Transporte', width: '75%', align: 'left' }];
  lstApendice4: any[] = [{ nombre: 'Clave SAAI FIII', width: '20%', align: 'center' }, { nombre: 'Clave SAAI M3', width: '20%', align: 'center' }, { nombre: 'País', width: '45%', align: 'left' }];
  lstApendice5: any[] = [{ nombre: 'País', width: '27%', align: 'center' }, { nombre: 'Clave de Moneda', width: '27%', align: 'center' }, { nombre: 'Nombre de Moneda', width: '27%', align: 'center' }];
  lstApendice6: any[] = [{ nombre: 'Aduana', width: '20%', align: 'center' }, { nombre: 'Clave', width: '20%', align: 'center' }, { nombre: 'Recinto Fiscalizado', width: '45%', align: 'left' }];
  lstApendice7: any[] = [{ nombre: 'Clave', width: '15%', align: 'center' }, { nombre: 'Descripción', width: '75%', align: 'center' }];
  lstApendice8: any[] = [{ nombre: 'Identificador', width: '15%', align: 'center' }, { nombre: 'Clave', width: '75%', align: 'left' }];
  lstApendice9: any[] = [{ nombre: 'Clave', width: '15%', align: 'center' }, { nombre: 'Descripción', width: '75%', align: 'left' }];
  lstApendice10: any[] = [{ nombre: 'Clave', width: '15%', align: 'center' }, { nombre: 'Descripción', width: '75%', align: 'left' }];
  lstApendice11: any[] = [{ nombre: 'Clave', width: '15%', align: 'center' }, { nombre: 'Descripción', width: '75%', align: 'left' }];
  lstApendice12: any[] = [{ nombre: 'Clave', width: '15%', align: 'center' }, { nombre: 'Contribución', width: '40%', align: 'left' }, { nombre: 'Abreviación', width: '15%', align: 'center' }, { nombre: 'Nivel', width: '15%', align: 'center' }];
  lstApendice13: any[] = [{ nombre: 'Clave', width: '15%', align: 'center' }, { nombre: 'Descripción', width: '75%', align: 'left' }];
  lstApendice14: any[] = [[{ nombre: '1', width: '15%', align: 'center' }, { nombre: '"C" Transporte Principal Pagado', width: '75%', align: 'left' }],
    [{ nombre: '2', width: '15%', align: 'center' }, { nombre: '"D" Llegada', width: '75%', align: 'left' }],
    [{ nombre: '3', width: '15%', align: 'center' }, { nombre: '"E" Salida', width: '75%', align: 'left' }],
    [{ nombre: '4', width: '15%', align: 'center' }, { nombre: '"F" Transporte Principal No Pagado', width: '75%', align: 'left' }]];
  lstApendice15: any[] = [{ nombre: 'Clave', width: '15%', align: 'center' }, { nombre: 'Descripción', width: '75%', align: 'left' }];
  lstApendice16: any[] = [{ nombre: 'Clave', width: '15%', align: 'center' }, { nombre: 'Descripción', width: '75%', align: 'left' }];
  lstApendice17: any[] = [{ nombre: 'Campo', width: '5%', align: 'center' }, { nombre: 'Pedimentos Normales', width: '13%', align: 'left' }, { nombre: 'Pedimentos Parte II', width: '13%', align: 'left' },
    { nombre: 'Pedimentos Copia Simple', width: '13%', align: 'left' }, { nombre: 'Facturas de Pedimentos Consolidados', width: '13%', align: 'left' }, { nombre: 'Relacion de Facturas de Pedimentos Consolidados', width: '15%', align: 'left' },
    { nombre: 'Longitud', width: '5%', align: 'center' }, { nombre: 'Formato', width: '7%', align: 'center' }];
  lstApendice18: any[] = [{ nombre: 'Clave', width: '15%', align: 'center' }, { nombre: 'Descripción', width: '75%', align: 'left' }];
  lstApendice20: any[] = [{ nombre: 'Campo', width: '15%', align: 'left' }, { nombre: 'Descripción', width: '75%', align: 'left' }];
  lstApendice21: any[] = [{ nombre: 'Aduana', width: '15%', align: 'left' }, { nombre: 'Inmueble habilitado para Recinto Fiscalizado Estratégico', width: '75%', align: 'left' }];
  lstHeaders: any[] = [
    this.lstApendice1,
    this.lstApendice2,
    this.lstApendice3,
    this.lstApendice4,
    this.lstApendice5,
    this.lstApendice6,
    this.lstApendice7,
    this.lstApendice8,
    this.lstApendice9,
    this.lstApendice10,
    this.lstApendice11,
    this.lstApendice12,
    this.lstApendice13,
    this.lstApendice14,
    this.lstApendice15,
    this.lstApendice16,
    this.lstApendice17,
    this.lstApendice18,
    null,
    this.lstApendice20,
    this.lstApendice21,
    null];
  lstSeccApendice2: any[] = ['Régimen Definitivo', 'Operaciones Virtuales', 'Temporales', 'IMMEX', 'Deposito Fiscal Almacenes Generales de Deposito (AGD)',
    'Locales Autorizados Para Exposiciones Internacionales de Mercancías que Ingresan al Regimen de Deposito Fiscal', 'Industria Automotriz (IA)',
    'Para Exposición y Venta de Mercancías en Tiendas Libres de Impuestos (DUTY FREE)', 'Transformación en Recinto Fiscalizado', 'Recintos Fiscalizados Estrategicos (RFE)',
    'Transitos', 'Otros'];
  lstSeccApendice9: any[] = ['Secretaría de Economía', 'Secretaría de Agricultura y Desarrollo Rural',
    'Secretaría de Salud', 'Secretaría de Medio Ambiente y Recursos Naturales', 'Comisión Intersecretaríal Para el Control del Proceso y Uso de Plaguicidas, Fertilizantes y Sustancias Toxicas',
    'Secretaría de la Defensa Nacional', 'Secretaría de Energía', 'Instituto Nacional de Antropología e Historia',
    'Instituto Nacional de Bellas Artes y Literatura', 'Consejo Nacional Mexicano del Café o los Consejos Estatales',
    'Consejo Regulador del Tequila', 'Comisión Reguladora de Energía'];

  divApendices: boolean = true;
  divTratados: boolean = false;
  divRegiones: boolean = false;
  lstApendices: any[] = [];
  lstTratados: any[] = [];
  lstRegiones: any[] = [];
  lstActive: any[] = [];
  lstActive2: any[] = [];
  lstActive3: any[] = [];

  numApendice: String;
  nombreApendice: String;
  nombreTratado: String;
  nombreRegion: String;
  lstCabeceras: String[] = [];
  lstCabecerasT: any[] = [{ nombre: 'Clave Anexo 22', width: '30%', align: 'center' }, { nombre: 'País', width: '60%', align: 'left' }];
  lstCabecerasR: any[] = [{ nombre: 'Clave Anexo 22', width: '20%', align: 'center' }, { nombre: 'País', width: '70%', align: 'center' }];
  cabecerasMultiples: Boolean;
  lstData: any[] = [];
  lstDataT: any[] = [];
  lstDataR: any[] = [];
  lstSecciones: any[] = [];
  minWidth: String = '';
  nota: String = '';

  placeholder: any = 'Buscar';
  searchTerm: string = null;
  resetSearch: boolean;
  arrayAux: any = [];
  search: any = [];

  searchVar:string = '';

  selectedOption:number = 0;

  constructor(private glosarioService: GlosarioService, private renderer: Renderer2, private router: Router) { }

  ngOnInit() {
    this.obtenerApendices();
    this.obtenerTratados();
    this.obtenerRegiones();
  }

  emitTab($event) {
    this.changeSearchTerm2('');
    DEBUG && console.log($event);
    switch ($event) {
      case "Apéndices":
        this.divApendices = true;
        this.divTratados = false;
        this.divRegiones = false;
        break;
      case "Tratados Internacionales":
        this.divApendices = false;
        this.divTratados = true;
        this.divRegiones = false;
        break;
      case 'Regiones':
        this.divApendices = false;
        this.divTratados = false;
        this.divRegiones = true;
        break;
    }
  }

  obtenerApendices() {
    SessionUser.getInstance().setLoading(true);
    this.glosarioService.obtenerApendices().subscribe(
      data => {
        DEBUG && console.log(data);
        this.lstApendices = data;
        this.lstActive = new Array(this.lstApendices.length).fill("");
        this.lstActive[0] = "itemActive";
        this.numApendice = data[0].numApendice;
        this.nombreApendice = data[0].nombreApendice;
        this.lstCabeceras = this.lstHeaders[0];

        let parametro: Parametros = new Parametros();
        parametro.id = this.lstApendices[0].idApendice;

        this.glosarioService.obtenerApendiceContenido(parametro).subscribe(
          data => {
            DEBUG && console.log(data);
            this.lstData = data;
            SessionUser.getInstance().setLoading(false);
          },
          error => {
            DEBUG && console.log('@obtenerApendices: failed', error);
            SessionUser.getInstance().setLoading(false);
            SessionUser.getInstance().setMessageFailed('al obtener los apéndices');
            document.getElementById('requestFailed').style.display = 'block';
          }
        );
      },
      error => {
        DEBUG && console.log('@obtenerApendices: failed', error);
        SessionUser.getInstance().setLoading(false);
        SessionUser.getInstance().setMessageFailed('al obtener los apéndices');
        document.getElementById('requestFailed').style.display = 'block';
      }
    );
  }

  obtenerTratados() {
    SessionUser.getInstance().setLoading(true);
    this.glosarioService.obtenerTratados().subscribe(
      data => {
        DEBUG && console.log(data);
        this.lstTratados = data;
        this.lstActive2 = new Array(this.lstTratados.length).fill("");
        this.lstActive2[0] = "itemActive";
        this.nombreTratado = data[0].nombreTratado;

        let parametro: Parametros = new Parametros();
        parametro.id = this.lstTratados[0].idTratado;

        this.glosarioService.obtenerTratadoContenido(parametro).subscribe(
          data => {
            DEBUG && console.log(data);
            this.lstDataT = data;
            SessionUser.getInstance().setLoading(false);
          },
          error => {
            DEBUG && console.log('@obtenerTratados: failed', error);
            SessionUser.getInstance().setLoading(false);
            SessionUser.getInstance().setMessageFailed('al obtener los tratados');
            document.getElementById('requestFailed').style.display = 'block';
          }
        );
      },
      error => {
        DEBUG && console.log('@obtenerTratados: failed', error);
        SessionUser.getInstance().setLoading(false);
        SessionUser.getInstance().setMessageFailed('al obtener los tratados');
        document.getElementById('requestFailed').style.display = 'block';
      }
    );
  }

  obtenerRegiones() {
    SessionUser.getInstance().setLoading(true);
    this.glosarioService.obtenerRegiones().subscribe(
      data => {
        DEBUG && console.log(data);
        this.lstRegiones = data;
        this.lstActive3 = new Array(this.lstRegiones.length).fill("");
        this.lstActive3[0] = "itemActive";
        this.nombreRegion = data[0].nombreRegion;

        let parametro: Parametros = new Parametros();
        parametro.id = this.lstRegiones[0].idRegion;

        this.glosarioService.obtenerRegionContenido(parametro).subscribe(
          data => {
            DEBUG && console.log(data);
            this.lstDataR = data;
            SessionUser.getInstance().setLoading(false);
          },
          error => {
            DEBUG && console.log('@obtenerRegiones: failed', error);
            SessionUser.getInstance().setLoading(false);
            SessionUser.getInstance().setMessageFailed('al obtener las regiones');
            document.getElementById('requestFailed').style.display = 'block';
          }
        );
      },
      error => {
        DEBUG && console.log('@obtenerRegiones: failed', error);
        SessionUser.getInstance().setLoading(false);
        SessionUser.getInstance().setMessageFailed('al obtener las regiones');
        document.getElementById('requestFailed').style.display = 'block';
      }
    );
  }

  activeItem(i: number) {
    SessionUser.getInstance().setLoading(true);
    this.lstActive = new Array(this.lstApendices.length).fill("");
    this.lstActive[i] = "itemActive";

    this.numApendice = this.lstApendices[i].numApendice;
    this.nombreApendice = this.lstApendices[i].nombreApendice;
    this.lstCabeceras = this.lstHeaders[i];

    let parametro: Parametros = new Parametros();
    parametro.id = this.lstApendices[i].idApendice;

    this.selectedOption = i + 1;

    switch (i) {
      case 1:
        this.lstSecciones = this.lstSeccApendice2;
        this.minWidth = '';
        this.nota = '';
        this.cabecerasMultiples = false;
        break;

      case 7:
        this.nota = 'En el pedimento se deberá asentar la clave del identificador a dos posiciones.\n El complemento que indique "no aplica", es un histórico y no debe ser utilizado.';
        break;

      case 8:
        this.lstSecciones = this.lstSeccApendice9;
        this.minWidth = '';
        this.nota = '';
        this.cabecerasMultiples = false;
        break;

      case 11:
        this.lstSecciones = [];
        this.minWidth = '675px';
        this.nota = '* Las claves “G” Y “P” son para indicar si se trata de un identificador a nivel global (pedimento) o a nivel partida.\n La clave “C”, corresponde a las contribuciones, cuotas compensatorias, gravamenes y derechos que pueden ser declarados en el pedimento complementario para la determinación y pago de contrbuciones por la aplicación del articulo 303 del TLCAN';
        this.cabecerasMultiples = false;
        break;

      case 13:
        this.lstSecciones = [];
        this.cabecerasMultiples = true;
        this.minWidth = '';
        this.nota = '';
        break;

      case 16:
        this.lstSecciones = [];
        this.cabecerasMultiples = false;
        this.minWidth = '1800px';
        this.nota = '';
        break;

      default:
        this.lstSecciones = [];
        this.minWidth = '';
        this.nota = '';
        this.cabecerasMultiples = false;
        break;
    }

    this.glosarioService.obtenerApendiceContenido(parametro).subscribe(
      data => {
        DEBUG && console.log(data);
        if (parametro.id == 17) {
          this.lstData = [];
          let order1 = [];
          let order2 = [];
          for (let item of data) {
            if (item.orden == 1) {
              order1.push(item);
            } else {
              order2.push(item);
            }
          }
          let i = 0;
          for (let item of order1) {
            let dataO = {
              column1: order1[i].column1,
              column2: order1[i].column2,
              column3: order1[i].column3,
              column4: order1[i].column4,
              column5: order2[i].column1,
              column6: order2[i].column2,
              column7: order2[i].column3,
              column8: order2[i].column4,
              idApendiceContenido: order1[i].idApendiceContenido,
              orden: order1[i].orden,
              renglon: order1[i].renglon,
              style: order1[i].style
            };
            this.lstData.push(dataO);
            i++;
          }

          DEBUG && console.log(this.lstData);
        } else {
          if (parametro.id == 19 || parametro.id == 22) {
            this.lstData = data;
            this.lstData = this.lstData.sort((a, b) => (a.renglon < b.renglon) ? -1 : 1);
          } else {
            this.lstData = data;
          }
        }
        SessionUser.getInstance().setLoading(false);
      },
      error => {
        DEBUG && console.log('@activeItem: failed', error);
        SessionUser.getInstance().setLoading(false);
        SessionUser.getInstance().setMessageFailed('al obtener el detalle');
        document.getElementById('requestFailed').style.display = 'block';
      }
    );
  }

  activeItem2(i: number, subTitulo: number) {
    if (subTitulo != 1) {
      SessionUser.getInstance().setLoading(true);
      this.lstActive2 = new Array(this.lstTratados.length).fill("");
      this.lstActive2[i] = "itemActive";
      this.nombreTratado = this.lstTratados[i].nombreTratado;

      let parametro: Parametros = new Parametros();
      parametro.id = this.lstTratados[i].idTratado;

      this.glosarioService.obtenerTratadoContenido(parametro).subscribe(
        data => {
          DEBUG && console.log(data);
          this.lstDataT = data;
          SessionUser.getInstance().setLoading(false);
        },
        error => {
          DEBUG && console.log('@activeItem2: failed', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener el detalle');
          document.getElementById('requestFailed').style.display = 'block';
        }
      );
    }

  }

  activeItem3(i: number) {
      SessionUser.getInstance().setLoading(true);
      this.lstActive3 = new Array(this.lstRegiones.length).fill("");
      this.lstActive3[i] = "itemActive";
      this.nombreRegion = this.lstRegiones[i].nombreRegion;

      let parametro: Parametros = new Parametros();
      parametro.id = this.lstRegiones[i].idRegion;

      if (i == 6 || i == 7 || i== 8) {
        this.lstCabecerasR = [{ nombre: 'País', width: '100%', align: 'center' }];
      } else {
        this.lstCabecerasR = [{ nombre: 'Clave Anexo 22', width: '20%', align: 'center' }, { nombre: 'País', width: '70%', align: 'center' }];
      }

      this.glosarioService.obtenerRegionContenido(parametro).subscribe(
        data => {
          DEBUG && console.log(data);
          this.lstDataR = data;
          SessionUser.getInstance().setLoading(false);
        },
        error => {
          DEBUG && console.log('@activeItem3: failed', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener el detalle');
          document.getElementById('requestFailed').style.display = 'block';
        }
      );


  }

  ngDoCheck(){

  }

  changeSearchTerm2($event){
    DEBUG && console.log($event);
    this.searchVar = $event;
  }

  changeSearchTerm($event) {
    /*
      let props = this.searchInText ($event, document.getElementById("tabla1").innerHTML);
      if (props.total > 0) document.getElementById("tabla1").innerHTML = props.html;
    */
  }


  /* searchInText (word, html) {
    //---Eliminar los spans
    html = html.replace(/<span >(.*?)<\/span>/g, "$1");

    //---Crear la expresión regular que buscará la palabra
    var reg = new RegExp(word.replace(/[\[\]\(\)\{\}\.\-\?\*\+]/, "\\$&"), "gi");
    var htmlreg = /<\/?(?:a|b|br|em|font|img|p|span|strong)[^>]*?\/?>/g;

    //---Añadir los spans
    var array;
    var htmlarray;
    var len = 0;
    var sum = 0;
    var pad = 28 + word.length;

    while ((array = reg.exec(html)) != null) {

        htmlarray = htmlreg.exec(html);

        //---Verificar si la búsqueda coincide con una etiqueta html
        if (htmlarray != null && htmlarray.index < array.index && htmlarray.index + htmlarray[0].length > array.index + word.length) {

            reg.lastIndex = htmlarray.index + htmlarray[0].length;

            continue;
        }

        len = array.index + word.length;

        html = html.slice(0, array.index) + "<span>" + html.slice(array.index, len) + "</span>" + html.slice(len, html.length);

        reg.lastIndex += pad;

        if (htmlarray != null) htmlreg.lastIndex = reg.lastIndex;

        sum++;

    }

    DEBUG && console.log(html);
    return {total: sum, html: html};
  } */

  redirectCalendar(){
    this.router.navigate(["protected/datastageCalendar"]);
  }

  filterData(data:any, index:any): any {
    if (this.selectedOption === 2 || this.selectedOption === 9 || this.selectedOption === 14) {
      return _.filter(data, ['orden', index]);
    }
    return data;
  }

}

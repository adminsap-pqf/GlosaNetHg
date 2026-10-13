import { Component, Input, OnInit, SimpleChange } from '@angular/core';
import { animate, style, transition, trigger } from '@angular/animations';
import { RegistrosService } from '../../services/registros/registros.service';
import { SessionUser } from '../../services/session/session.service';
import * as moment from 'moment';
import _ from 'lodash';

import { DEBUG } from '../../settings/app.settings';

const EXCEPTION_SEARCH = 'EXCEPTION_SEARCH';
const EXCEPTION_ORDER = 'EXCEPTION_ORDER';


@Component({
  selector: 'gn-historial-registro',
  templateUrl: './historial-registro.component.html',
  styleUrls: ['./historial-registro.component.scss'],
  animations: [
    trigger('fadeInOut', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate(400, style({ opacity: 0.7 }))
      ]),
      transition(':leave', [
        animate(400, style({ opacity: 0 }))
      ])
    ])
  ]
})
export class HistorialRegistroComponent implements OnInit {

  @Input() listRecords: any[] = [];
  @Input() loading: boolean = false;
  listRecordsUniverse = [];
  listRecordsProcessToShow = [];
  optionsOrderBy:any[] = [
    {nombre: 'Más Nuevo'},
    {nombre: 'Más Antiguos'},
  ];
  selectedOrderBy:any = {nombre: 'Más Nuevo'};
  searchTerm:string = '';

  constructor(private registrosService: RegistrosService) { }

  ngOnInit() {
  }

  ngOnChanges(changes: { [propertyName: string]: SimpleChange }) {
    if (changes.listRecords && changes.listRecords.previousValue &&
        changes.listRecords.currentValue.length > 0) {
      // Obtenemos copia del universo, para reealizar filtros de busquedad
      this.listRecordsUniverse  = this.patchRecord(this.listRecords);
      this.processRecordsForPresentation(this.listRecordsUniverse);
    }
  }

  handleOrderByDate($event) {
    DEBUG && console.log('@handleOrderBy: Entre ', $event);
    if (_.isEqual($event, this.selectedOrderBy)) return;
    this.selectedOrderBy = $event;
    const result = this.orderByDate(this.listRecordsUniverse);
    this.processRecordsForPresentation(result, [EXCEPTION_ORDER]);
  }

  orderByDate(collection) {
    const orientation = this.selectedOrderBy.nombre === 'Más Nuevo' ? 'desc' : 'asc';
    const result = _.orderBy(collection, [ o => { const date = new Date(o.ffin); return date.getTime()} ], orientation);
    return result.slice();
  }

  handleKeySearch($event) {
    DEBUG && console.log('@handleKeySearch: Entre ', $event);
    this.searchTerm = $event;
    const result = this.keySearch(this.listRecordsUniverse);
    this.processRecordsForPresentation(result, [EXCEPTION_SEARCH]);
  }

  keySearch(collection) {
    if (this.searchTerm === '') return collection;

    return _.filter(collection, o => {
      return o.folioRegistro.toString().toLowerCase().indexOf(this.searchTerm.toLowerCase()) !== -1 ||
        o.tramiteCofepris.toString().toLowerCase().indexOf(this.searchTerm.toLowerCase()) !== -1;
    });
  }

  processRecordsForPresentation(collection, filterExceptionList = []) {

    if (_.indexOf(filterExceptionList, EXCEPTION_SEARCH) === -1) {
      collection = this.keySearch(collection);
    }

    if ( _.indexOf(filterExceptionList, EXCEPTION_ORDER) === -1 ) {
      collection = this.orderByDate(collection);
    }

    this.listRecordsProcessToShow = collection.slice();
  }

  patchRecord(collection) {
    const result = [];
    _.forEach(collection, record => {
      const events = this.pathEvent(record);
      result.push({
        ...record,
        eventos: events.slice(),
        contentIsOpen: false,
        fileHistoryByRecord: false
      });
    });
    return result.slice();
  }

  pathEvent(record) {
    const events = [];
    const today = new Date();
    _.forEach(record.eventos, event => {
      const currentDay = moment(today);
      const endDate = moment(record.ffin);
      const manager =  _.concat(event.correos, event.correos, event.correos);
      const total = manager.length;
      let result  = '';
      _.forEach(manager, (contact, index) => {
        result += `${contact.correo}${total !== (index + 1) ? ' · ' : ''}`;
      });
      events.push({
        ...event,
        manager: result,
        deadline: currentDay.diff(endDate, 'days')
      });
    });
    return events.slice();
  }

  getPdf(record, item) {
    SessionUser.getInstance().setLoading(true);
    this.registrosService.getPdf(record.idEmpresa, record.idRegistro, item.idEvento, item.idHistorial)
      .subscribe(
        data => {
          this.downloadFile(data, 'Doc_' + item.idHistorial + '.pdf');
        },
        error => {
          DEBUG && console.log('Error al traer el pdf: ', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al descargar el archivo');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }

  getHistorialProoductoPDF(record, filename) {
    SessionUser.getInstance().setLoading(true);
    this.registrosService.getHistorialProductoPDF(record.idEmpresa, record.producto.idProducto, record.idRegistro, filename)
      .subscribe(
        data => {
          this.downloadFile(data, filename);
        },
        error => {
          DEBUG && console.log('Error al traer el pdf: ', error);
          SessionUser.getInstance().setLoading(false);
        });
  }

  downloadFile(data, filename) {
    if (data) {
      const filePDF = 'data:application/pdf;base64,' + data;
      const element = document.createElement('a');
      element.setAttribute('href', filePDF);
      element.setAttribute('download', filename);
      element.style.display = 'none';
      document.body.appendChild(element);
      element.click();
      document.body.removeChild(element);
    } else {
      SessionUser.getInstance().setMessageFailed('al descargar el archivo');
      document.getElementById('requestFailed').style.display = 'block';
    }
    SessionUser.getInstance().setLoading(false);
  }

}

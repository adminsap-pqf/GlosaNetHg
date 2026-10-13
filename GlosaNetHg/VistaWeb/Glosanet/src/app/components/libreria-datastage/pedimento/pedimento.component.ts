import { Component, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import * as _ from 'lodash';
import { SessionUser } from '../../../services/session/session.service';
import { DatastageService } from '../../../services/datastage/datastage.service';
import { PAGING_LIMIT } from '../../../util/common.protocols';
import * as Column from '../../../model/column.class';
import { ColumnInterface } from '../../../interfaces/column.interface';

/* Dev Tools */
import { DEBUG } from '../../../settings/app.settings';

const EXCEPTION_SEARCH = 'EXCEPTION_SEARCH';
const EXCEPTION_FILTERS_BY_TYPE_OPERATION = 'EXCEPTION_FILTERS_BY_TYPE_OPERATION';
const EXCEPTION_FILTERS_BY_TYPE_PEDIMENTO = 'EXCEPTION_FILTERS_BY_TYPE_PEDIMENTO';
const EXCEPTION_START_DATE = 'EXCEPTION_START_DATE';
const EXCEPTION_END_DATE = 'EXCEPTION_END_DATE';

@Component({
    selector: 'gn-pedimento',
    templateUrl: './pedimento.component.html',
    styleUrls: ['./pedimento.component.scss']
})
export class PedimentoComponent implements OnInit, OnDestroy, AfterViewInit {
    subscription: Subscription;

    optionsTipoOperacion: any[] = [
        {nombre: 'Todos', id: -1},
        {nombre: 'Importación', id: 1},
        {nombre: 'Exportación', id: 2},
    ];
    selectedTipoOperacion: any = {nombre: 'Todos', id: -1};

    optionsTipoPedimento: any[] = [
        { nombre: 'Todos', id: -1 },
        { nombre: 'Normal', id: 1 },
        { nombre: 'Reactificación', id: 2 },
        { nombre: 'Consolidado', id: 3 },
        { nombre: 'Transito', id: 4 },
        { nombre: 'Industría Automotriz', id: 5 },
        { nombre: 'Complementario', id: 6 },
        { nombre: 'Desistido', id: 7 },
        { nombre: 'Complementario', id: 8 },
        { nombre: 'Rectificación Complementario', id: 9 },
        { nombre: 'Pedimento de Transito', id: 11 }
    ];
    selectedTipoPedimento: any = {nombre: 'Todos', id: -1};

    idDataStage = 0;
    dataStageStartDate = null;
    dataStageEndDate: null;
    dataStageFolio = 0;
    dataStageTotalValue = 0;

    startDate: string = null;
    startDateFormat = '';
    invalidStartDate = false;
    endDate: string = null;
    endDateFormat = '';
    invalidEndDate = false;

    searchTerm: string = null;

    grandTotal = 0;
    currentTotal = 0;
    currentPath = '';
    currentPage = 0;

    firstItem = {};
    loading = false;
    buffer: ColumnInterface[][] = [];
    _isMounted = false;

    tableHead: any [] = Column.headsPedimento;

    // Configuración por columnas
    settings: any [] = Column.settingsPedimento;

    handleKeySearch = _.debounce($event => this.changeSearchTerm($event), 500);

    constructor(
        private datastageService: DatastageService,
        private router: Router,
        private activatedRoute: ActivatedRoute
    ) {
        this.subscription = this.activatedRoute.params.subscribe(params => {
            this.initialState(params);
        });
    }

    ngOnInit() { }

    ngAfterViewInit() {
        this._isMounted = true; // TODO: Evitamos llamadas innecesarias de los droplist cuando aun el componente no esta montado
    }

    ngOnDestroy() {
        if (this.subscription) {
            this.subscription.unsubscribe();
        }
    }

    initialState(params) {
        if (_.isEmpty(params)) {
        } else {
            const storage = localStorage.getItem('currentUser');
            if (storage) {
                const packedData = JSON.parse(storage);
                this.idDataStage = parseInt(params['id'], 10);
                if (packedData.currentDataStage && this.idDataStage === packedData.currentDataStage.idDataStage) {
                    this.dataStageStartDate = packedData.currentDataStage.startDate;
                    this.dataStageEndDate = packedData.currentDataStage.endDate;
                    this.dataStageFolio = packedData.currentDataStage.folio;
                    this.dataStageTotalValue = packedData.currentDataStage.totalValue;
                    this.loadData();
                } else {
                    this.redirectTo('protected/datastagelibrary');
                }
            }
        }
    }

    async loadData() {
        SessionUser.getInstance().setLoading(true);
        const data: any = await this.getData(); // TODO: Vamos por la primer página
        this.buffer = data.slice();
    }

    redirectTo(path) {
        this.router.navigate([`protected/${path}`]);
    }

    redirectToDetail(value) {
        let packedData = localStorage.getItem('currentUser');
        if (packedData) {
            packedData = JSON.parse(packedData);
            _.update(packedData, 'currentPedimento', () => {
                return {
                    idDataStage: this.idDataStage,
                    startDate: this.dataStageStartDate,
                    endDate: this.dataStageEndDate,
                    folio: this.dataStageFolio,
                    totalValue: this.dataStageTotalValue,
                    type: value.type,
                    pedimento: value.data.pedimento,
                    patente: value.data.patente,
                    seccionAduanera: value.data.seccionAduanera,
                    comments: value.data.observaciones,
                    idGeneral: false
                };
            });
            localStorage.setItem('currentUser', JSON.stringify(packedData));
            this.router.navigate([`protected/detallePedimento`]);
        }
    }

    handleFilterByTipoOperacion($event) {
        // tslint:disable-next-line:no-unused-expression
        DEBUG && console.log('@handleFilterByTipoOperacion: Entre ', $event);
        this.selectedTipoOperacion = $event;
        const path = this.getFilterByTipoOperacion();
        this.processDataForPresentation(path, [EXCEPTION_FILTERS_BY_TYPE_OPERATION]);
    }

    getFilterByTipoOperacion() {
        if (this.selectedTipoOperacion.nombre === 'Todos') { return ''; }
        return `&filterTipoOperacion=${this.selectedTipoOperacion.id}`;
    }

    handleFilterByTipoPedimento($event) {
        // tslint:disable-next-line:no-unused-expression
        DEBUG && console.log('@handleFilterByTipoPedimento: Entre ', $event);
        this.selectedTipoPedimento = $event;
        const path = this.getFilterByTipoPedimento();
        this.processDataForPresentation(path, [EXCEPTION_FILTERS_BY_TYPE_PEDIMENTO]);
    }

    getFilterByTipoPedimento() {
        if (this.selectedTipoPedimento.nombre === 'Todos') { return ''; }
        return `&filterTipoPedimento=${this.selectedTipoPedimento.id}`;
    }

    async handleStartDate(date) {
        // tslint:disable-next-line:no-unused-expression
        DEBUG && console.log('@handleStartDate: Entre');
        const invalid = await this.validateDates(date, 1);
        if (invalid || this.invalidEndDate) {
            if (!invalid) {
                this.startDateFormat = `${this.formatDate(date)} 00:00:00`;
            } else {
                this.startDateFormat = '';
            }
            return;
        }
        this.startDateFormat = `${this.formatDate(date)} 00:00:00`;
        const path = this.getStartDate();
        this.processDataForPresentation(path, [EXCEPTION_START_DATE]);
    }

    getStartDate() {
        if (this.startDateFormat === '') { return ''; }
        return `&filterInitialDate=${this.startDateFormat}`;
    }

    resetStartDate() {
        this.startDate = null;
        this.startDateFormat = '';
        this.invalidStartDate = false;
        this.processDataForPresentation('', [EXCEPTION_START_DATE]);
    }

    async handleEndDate(date) {
        // tslint:disable-next-line:no-unused-expression
        DEBUG && console.log('@handleEndDate: Entre');
        const invalid = await this.validateDates(date, 2);
        if (invalid || this.invalidStartDate) {
            if (!invalid) {
                this.endDateFormat = `${this.formatDate(date)} 23:59:59`;
            } else {
                this.endDateFormat = '';
            }
            return;
        }
        this.endDateFormat = `${this.formatDate(date)} 23:59:59`;
        const path = this.getEndDate();
        this.processDataForPresentation(path, [EXCEPTION_END_DATE]);
    }

    getEndDate() {
        if (this.endDateFormat === '') { return ''; }
        return `&filterFinalDate=${this.endDateFormat}`;
    }

    resetEndDate() {
        this.endDate = null;
        this.endDateFormat = '';
        this.invalidEndDate = null;
        this.processDataForPresentation('', [EXCEPTION_END_DATE]);
    }

    validateDates(date, option) {
        return new Promise(resolve => {
            setTimeout(() => {

                if (option === 1 && this.endDate === null) {
                    return resolve(false);
                }

                if (option === 2 && this.startDate === null) {
                    return resolve(false);
                }

                const startTime = new Date(this.startDate);
                startTime.setHours(0, 0, 0, 0);
                const endTime = new Date(this.endDate);
                endTime.setHours(23, 59, 59, 999);
                const currentDate = new Date(date);
                let valid;
                if ((option === 1 && currentDate.getTime() <= endTime.getTime())
                    || (option === 2 && startTime.getTime() <= currentDate.getTime())) {
                    if (option === 1) {
                        this.invalidStartDate = false;
                    } else if (option === 2) {
                        this.invalidEndDate = false;
                    }
                    valid = false;
                } else {
                    if (option === 1) {
                        this.invalidStartDate = true;
                    } else if (option === 2) {
                        this.invalidEndDate = true;
                    }
                    valid = true;
                }
                resolve(valid);
            });
        });
    }

    formatDate(date) {
        const d = new Date(date),
            year = d.getFullYear();
        let month = '' + (d.getMonth() + 1),
            day = '' + d.getDate();
        if (month.length < 2) {
            month = '0' + month;
        }
        if (day.length < 2) {
            day = '0' + day;
        }
        return [year, month, day].join('-');
    }

    updateData() {
        this.reset();
    }

    changeSearchTerm($event) {
        // tslint:disable-next-line:no-unused-expression
        DEBUG && console.log('@handleKeySearch: Entre ', $event);
        this.searchTerm  = $event;
        const path = this.getSearchTerm();
        this.processDataForPresentation(path, [EXCEPTION_SEARCH]);
    }

    getSearchTerm() {
        if (!this.searchTerm) { return ''; }
        return `&searchTerm=${this.searchTerm}`;
    }

    processDataForPresentation(filter = '', filterExceptionList = []) {
        if (!this._isMounted) { return; }

        if ( _.indexOf(filterExceptionList, EXCEPTION_SEARCH) === -1 ) {
            filter += this.getSearchTerm();
        }

        if ( _.indexOf(filterExceptionList, EXCEPTION_FILTERS_BY_TYPE_OPERATION) === -1 ) {
            filter += this.getFilterByTipoOperacion();
        }

        if ( _.indexOf(filterExceptionList, EXCEPTION_FILTERS_BY_TYPE_PEDIMENTO) === -1 ) {
            filter += this.getFilterByTipoPedimento();
        }

        if ( _.indexOf(filterExceptionList, EXCEPTION_START_DATE) === -1 ) {
            filter += this.getStartDate();
        }

        if ( _.indexOf(filterExceptionList, EXCEPTION_END_DATE) === -1 ) {
            filter += this.getEndDate();
        }

        this.currentPage = 0;
        this.currentPath = `${filter}`;
        this.reset();
    }

    getData = (updateData = false) => {
        return new Promise((resolve, reject) => {
            const numItems = Math.floor((window.screen.width - 110) / 225) * 4;
            const size = PAGING_LIMIT < numItems ? numItems : PAGING_LIMIT;
            const url = `page=${this.currentPage}&size=${size}${this.currentPath}&idDatastage=${this.idDataStage}`;
            // tslint:disable-next-line:no-unused-expression
            DEBUG && console.log('@getData: URL ' + url);
            this.datastageService.getPedimentos(url).subscribe(
                data => {
                    SessionUser.getInstance().setLoading(false);
                    const { results, total } = data;
                    const result: ColumnInterface[][] = this.patchObject(results);
                    this.grandTotal = this.currentTotal === 0 && this.grandTotal === 0 && total > 0 ? total : this.grandTotal;
                    this.firstItem = result.length > 0 && this.currentPage === 0 ? result[0] : this.firstItem;
                    this.currentTotal = total;
                    this.loading = false;
                    if (updateData) {
                        this.buffer = this.buffer.concat(result);
                    }
                    resolve(result.slice());
                }, err => {
                    DEBUG && console.log('@getData: Error ', err);
                    SessionUser.getInstance().setLoading(false);
                    SessionUser.getInstance().setMessageFailed('al obtener los registros');
                    resolve([]);
                    this.loading = false;
                });
        });
    }

    patchObject(collection) {
        const result: ColumnInterface[][] = [];
        const defaultColumn: ColumnInterface = new Column.ColumnClass();

        _.forEach(collection, o => {
            result.push([
                {
                    ...defaultColumn,
                    label: o.recordIndex,
                },
                {
                  ...defaultColumn,
                    activeIco: true
                },
                {
                    ...defaultColumn,
                    isGroup: true,
                    label: `Aduanera: ${o.seccionAduanera}`,
                    subLabel: `Aduanera Entrada: ${o.seccionAduaneraEntrada}`
                },
                {
                    ...defaultColumn,
                    label: o.pedimento
                },
                {
                    ...defaultColumn,
                    label: o.patente
                },
                {
                    ...defaultColumn,
                    label: this.getTipoOperacion(o.tipoOperacion)
                },
                {
                    ...defaultColumn,
                    label: o.claveDocumento
                },
                {
                    ...defaultColumn,
                    label: this.getTipoPedimento(o.tipoPedimento)
                },
                {
                    ...defaultColumn,
                    isDate: true,
                    label: o.fechaRecepcionPedimento
                },
                {
                    ...defaultColumn,
                    isDate: true,
                    label: o.fechaPagoReal
                },
                {
                    ...defaultColumn,
                    isLink: true,
                    label: 'Ver Detalle',
                    parent: o,
                    linkType: 'factura'
                },
                {
                    ...defaultColumn,
                    isLink: true,
                    label: 'Ver Detalle',
                    parent: o,
                    linkType: 'eDoc'
                },
                {
                    ...defaultColumn,
                    isLink: true,
                    label: 'Ver Detalle',
                    parent: o,
                    linkType: 'partida'
                },
                {
                    ...defaultColumn,
                    isComment: true,
                    label: 'Ver Comentarios',
                    parent: o,
                    comment: o.observaciones
                }
            ]);
        });
        return result.slice();
    }

    getTipoOperacion(type) {
        let result;
        switch (parseInt(type, 10)) {
            case 1:
                result = 'Importación';
                break;
            case 2:
                result = 'Exportación';
                break;
            default:
                result = 'N/A';
                break;
        }
        return  result;
    }

    getTipoPedimento(type) {
        let result;
        switch (parseInt(type, 10)) {
            case 1:
                result = 'Normal';
                break;
            case 2:
                result = 'Reactificación';
                break;
            case 3:
                result = 'Consolidado';
                break;
            case 4:
                result = 'Transito';
                break;
            case 5:
                result = 'Industría Automotriz';
                break;
            case 6:
                result = 'Complementario';
                break;
            case 7:
                result = 'Desistido';
                break;
            case 8:
                result = 'Reservado';
                break;
            case 9:
                result = 'Rectificación Complementario';
                break;
            case 11:
                result = 'Pedimento de Transito';
                break;
            default:
                result = 'N/A';
                break;
        }
        return  result;
    }

    handleCurrentPage(value) {
        this.currentPage = value;
    }

    handleLoading(value) {
        this.loading = value;
    }

    async reset() {
        this.loading = true;
        const data: any = await this.getData();
        this.buffer = data.slice();
        this.loading = false;
    }
}

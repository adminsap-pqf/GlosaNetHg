import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import * as _ from 'lodash';
import { DatastageService } from '../../services/datastage/datastage.service';
import { SessionUser } from '../../services/session/session.service';
import * as Column from '../../model/column.class';
import { ColumnInterface } from '../../interfaces/column.interface';
import { PAGING_LIMIT } from '../../util/common.protocols';

import { DEBUG } from '../../settings/app.settings';

const EXCEPTION_START_DATE = 'EXCEPTION_START_DATE';
const EXCEPTION_END_DATE = 'EXCEPTION_END_DATE';
const EXCEPTION_SEARCH_BY_PEDIMENTO = 'EXCEPTION_SEARCH_BY_PEDIMENTO';

@Component({
    selector: 'gn-search-pedimento',
    templateUrl: './search-pedimento.component.html',
    styleUrls: ['./search-pedimento.component.scss']
})
export class SearchPedimentoComponent implements OnInit, AfterViewInit {

    activeDates = false;
    startDate: Date = null;
    startDateFormat = '';
    invalidStartDate = false;
    endDate: Date = null;
    endDateFormat = '';
    invalidEndDate = false;

    grandTotal = 0;
    currentTotal = 0;
    currentPath = '';
    currentPage = 0;
    _isMounted = false;

    firstItem = {};
    loading = false;
    buffer: ColumnInterface[][] = [];

    tableHead: any [] = Column.headsPedimento;
    settings: any [] = Column.settingsPedimento;

    showTotal = false;
    withoutMessage = 'Ingresa los datos de busqueda para habilitar esta sección';

    searchByPedimento = '';
    handleKeySearchByPedimento = _.debounce($event => this.changeSearchByPedimento($event), 500);

    constructor(
        private datastageService: DatastageService,
        private router: Router
    ) { }

    ngOnInit() {
        const today = new Date();
        this.startDate = new Date(today.getFullYear(), 0, 1);
        this.startDateFormat = `${this.formatDate(this.startDate)} 00:00:00`;
        this.endDate = new Date(today.getFullYear(), 11, 31);
        this.endDateFormat = `${this.formatDate(this.endDate)} 00:00:00`;
    }

    ngAfterViewInit() {
        this._isMounted = true; // TODO: Evitamos llamadas innecesarias de los droplist cuando aun el componente no esta montado
    }

    redirectToDetail(value) {
        let packedData = localStorage.getItem('currentUser');
        if (packedData) {
            packedData = JSON.parse(packedData);
            _.update(packedData, 'currentPedimento', () => {
                return {
                    idDataStage: value.data.idDatastage,
                    startDate: new Date(),
                    endDate: new Date(),
                    folio: '',
                    totalValue: '-',
                    type: value.type,
                    pedimento: value.data.pedimento,
                    patente: value.data.patente,
                    seccionAduanera: value.data.seccionAduanera,
                    comments: value.data.observaciones,
                    idGeneral: true
                };
            });
            localStorage.setItem('currentUser', JSON.stringify(packedData));
            this.router.navigate([`protected/detallePedimento`]);
        }
    }

    handleCurrentPage(value) {
        this.currentPage = value;
    }

    handleLoading(value) {
        this.loading = value;
    }

    changeSearchByPedimento(value: string) {
        this.searchByPedimento = value.trim();
        const path = this.getSearchByPedimento();
        this.processDataForPresentation(path, [EXCEPTION_SEARCH_BY_PEDIMENTO]);
    }

    getSearchByPedimento() {
        if (!this.searchByPedimento) { return ''; }
        return `&pedimento=${this.searchByPedimento}`;
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
        if (this.activeDates) {
            const path = this.getStartDate();
            this.processDataForPresentation(path, [EXCEPTION_START_DATE]);
        }
    }

    getStartDate() {
        if (this.startDateFormat === '') {
            return '';
        }
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
        if (this.activeDates) {
            const path = this.getEndDate();
            this.processDataForPresentation(path, [EXCEPTION_END_DATE]);
        }
    }

    getEndDate() {
        if (this.endDateFormat === '') {
            return '';
        }
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

    processDataForPresentation(filter = '', filterExceptionList = []) {
        if (!this._isMounted) {
            return;
        }

        if (_.indexOf(filterExceptionList, EXCEPTION_SEARCH_BY_PEDIMENTO) === -1) {
            filter += this.getSearchByPedimento();
        }

        if (_.indexOf(filterExceptionList, EXCEPTION_START_DATE) === -1) {
            filter += this.getStartDate();
        }

        if (_.indexOf(filterExceptionList, EXCEPTION_END_DATE) === -1) {
            filter += this.getEndDate();
        }

        this.currentPage = 0;
        this.currentPath = `${filter}`;
        this.reset();
    }

    updateData() {
        this.activeDates = true;
        this.processDataForPresentation();
    }

    async reset() {
        this.loading = true;
        const data: any = await this.getData();
        this.buffer = data.slice();
        this.loading = false;
    }

    getData = (updateData = false) => {
        return new Promise((resolve, reject) => {
            const numItems = Math.floor((window.screen.width - 110) / 225) * 4;
            const size = PAGING_LIMIT < numItems ? numItems : PAGING_LIMIT;
            const url = `page=${this.currentPage}&size=${size}${this.currentPath}` +
                `&idCompany=${SessionUser.getInstance().getIdClient()}`;
            // tslint:disable-next-line:no-unused-expression
            DEBUG && console.log('@getData: URL ' + url);
            this.datastageService.getPedimentos(url).subscribe(
                data => {
                    SessionUser.getInstance().setLoading(false);
                    const {results, total} = data;
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
                    // tslint:disable-next-line:no-unused-expression
                    DEBUG && console.log('@getData: Error ', err);
                    SessionUser.getInstance().setLoading(false);
                    SessionUser.getInstance().setMessageFailed('al obtener los registros');
                    document.getElementById('requestFailed').style.display = 'block';
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
}

import { Component, OnInit, AfterViewInit, OnDestroy } from '@angular/core';
import * as _ from 'lodash';
import { DatastageService } from '../../services/datastage/datastage.service';
import { VucemTokenService } from '../../services/vucem-token/vucem-token.service';
import { SessionUser } from '../../services/session/session.service';
import * as Column from '../../model/column.class';
import { ColumnInterface } from '../../interfaces/column.interface';
import * as protocols from '../../util/common.protocols';

import { DEBUG } from '../../settings/app.settings';

const EXCEPTION_START_DATE = 'EXCEPTION_START_DATE';
const EXCEPTION_END_DATE = 'EXCEPTION_END_DATE';
const EXCEPTION_SEARCH_BY_PEDIMENTO = 'EXCEPTION_SEARCH_BY_PEDIMENTO';
const EXCEPTION_SEARCH_BY_COVE = 'EXCEPTION_SEARCH_BY_COVE';
const LINK_TYPE_FACTURA = 'LINK_TYPE_FACTURA';

@Component({
    selector: 'gn-search-factura',
    templateUrl: './search-factura.component.html',
    styleUrls: ['./search-factura.component.scss']
})
export class SearchFacturaComponent implements OnInit, AfterViewInit, OnDestroy {

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

    tableHead: any [] = Column.headsFactura;
    settings: any [] = Column.settingsFactura;

    showTotal = false;
    withoutMessage = 'Ingresa los datos de busqueda para habilitar esta sección';
    vucemTokenActive = 0;

    searchByPedimento = '';
    handleKeySearchByPedimento = _.debounce($event => this.changeSearchByPedimento($event), 500);
    searchByCove = '';
    handleKeySearchByCove = _.debounce($event => this.changeSearchByCove($event), 500);

    realTimer: any;

    constructor(
        private datastageService: DatastageService,
        private vucemTokenService: VucemTokenService
    ) { }

    ngOnInit() {
        const today = new Date();
        this.startDate = new Date(today.getFullYear(), 0, 1);
        this.startDateFormat = `${this.formatDate(this.startDate)} 00:00:00`;
        this.endDate = new Date(today.getFullYear(), 11, 31);
        this.endDateFormat = `${this.formatDate(this.endDate)} 00:00:00`;

        const customHead = this.tableHead.slice();
        customHead.splice(1, 0, { label: 'Pedimento' });
        this.tableHead = customHead.slice();

        const customSettings = this.settings.slice();
        customSettings.splice(1, 0, { width: '150px', align: 'center' });
        this.settings = customSettings.slice();
        this.validateToken();
    }

    ngAfterViewInit() {
        this._isMounted = true; // TODO: Evitamos llamadas innecesarias de los droplist cuando aun el componente no esta montado
    }

    ngOnDestroy() {
        if (this.realTimer) {
            this.clearRealTimer();
        }
    }

    validateToken() {
        const currentTokenVucem = SessionUser.getInstance().getVucemToken();
        if (currentTokenVucem) {
            this.vucemTokenActive = currentTokenVucem.activo? 1:-1;
        } else {
            this.vucemTokenService.getToken(SessionUser.getInstance().getIdClient())
                .subscribe(data => {
                    const { idCliente, lastUpdateDate, pkToken, token, activo } = data;
                    if (idCliente && lastUpdateDate && pkToken && token) {
                        this.vucemTokenActive = activo? 1:-1;
                        SessionUser.getInstance().setVucemToken({
                            idToken: pkToken,
                            token: token,
                            lastUpdateDate: lastUpdateDate,
                            activo: activo
                        });
                    }
                    SessionUser.getInstance().setLoading(false);
                });
        }
    }

    handleSingleDownload(value) {
        if (value.type === LINK_TYPE_FACTURA) {
            this.requestFactura(value.data);
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
        if (!this.searchByPedimento) {
            return '';
        }
        return `&pedimento=${this.searchByPedimento}`;
    }

    changeSearchByCove(value: string) {
        this.searchByCove = value.trim();
        const path = this.getSearchByCove();
        this.processDataForPresentation(path, [EXCEPTION_SEARCH_BY_COVE]);
    }

    getSearchByCove() {
        if (!this.searchByCove) {
            return '';
        }
        return `&searchTerm=${this.searchByCove}`;
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

        if (_.indexOf(filterExceptionList, EXCEPTION_SEARCH_BY_COVE) === -1) {
            filter += this.getSearchByCove();
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
        this.handleCheckInRealTime();
    }

    getData = (updateData = false) => {
        return new Promise((resolve, reject) => {
            const numItems = Math.floor((window.screen.width - 110) / 225) * 4;
            const size = protocols.PAGING_LIMIT < numItems ? numItems : protocols.PAGING_LIMIT;
            const url = `page=${this.currentPage}&size=${size}${this.currentPath}` +
                `&idCompany=${SessionUser.getInstance().getIdClient()}`;
            // tslint:disable-next-line:no-unused-expression
            DEBUG && console.log('@getData: URL ' + url);
            this.datastageService.getFacturas(url).subscribe(
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
    };

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
                    label: o.pedimento
                },
                {
                    ...defaultColumn,
                    label: o.numeroFactura
                },
                {
                    ...defaultColumn,
                    isDate: true,
                    label: o.fechaFacturacion
                },
                {
                    ...defaultColumn,
                    label: o.terminoFacturacion
                },
                {
                    ...defaultColumn,
                    label: o.proveedorMercancia
                },
                {
                    ...defaultColumn,
                    label: o.calleProveedor ? o.calleProveedor : 'N/A'
                },
                {
                    ...defaultColumn,
                    isStatus: o.numeroFactura.indexOf('COVE') !== -1,
                    label: o.numeroFactura.indexOf('COVE') !== -1 ? this.getStatus(o.status) : 'N/A',
                    errorMessage: o.status === protocols.RABBITMQ_JOB_STATE_FAILURE && o.errorMessage
                        ? o.errorMessage
                        : '',
                    parent: o,
                    status: o.status,
                    linkType: LINK_TYPE_FACTURA
                }
            ]);
        });
        return result.slice();
    }

    getStatus(status) {
        let text = '';
        switch (status) {
            case protocols.RABBITMQ_JOB_STATE_NONE:
                text = protocols.LABEL_STATE_NONE;
                break;
            case protocols.RABBITMQ_JOB_STATE_RECEIVED:
                text = protocols.LABEL_STATE_RECEIVED;
                break;
            case protocols.RABBITMQ_JOB_STATE_PROCESSING:
                text = protocols.LABEL_STATE_PROCESSING;
                break;
            case protocols.RABBITMQ_JOB_STATE_FAILURE:
                text = protocols.LABEL_STATE_FAILURE;
                break;
            case protocols.RABBITMQ_JOB_STATE_SUCCESS:
                text = protocols.LABEL_STATE_SUCCESS;
                break;
        }
        return text;
    }

    requestFactura(data) {
        SessionUser.getInstance().setLoading(true);
        let term = {};
        if (data.status === protocols.RABBITMQ_JOB_STATE_NONE) {
            term = {
                idSolicitud: null,
                idFactura: data.idFactura
            };
        } else if (data.status === protocols.RABBITMQ_JOB_STATE_FAILURE) {
            term = {
                idSolicitud: data.idSolicitudFactura,
                numeroFactura: data.numeroFactura
            };
        }
        this.datastageService.requestFactura(term).subscribe(
            response => {
                // tslint:disable-next-line:no-unused-expression
                DEBUG && console.log('@requestFactura: error =>', response);
                this.setStatus(data);
                SessionUser.getInstance().setLoading(false);
            },
            error => {
                // tslint:disable-next-line:no-unused-expression
                DEBUG && console.log('@requestFactura: error =>', error);
                SessionUser.getInstance().setLoading(false);
                SessionUser.getInstance().setMessageFailed('en la solicitud');
                document.getElementById('requestFailed').style.display = 'block';
            });
    }

    setStatus(data) {
        let indexParent = -1;
        let indexChild = -1;
        _.forEach(this.buffer, (item, indexP) => {
            const index = _.findIndex(item, o => {
                return o.parent.idFactura === data.idFactura;
            });
            if (index !== -1) {
                indexParent = indexP;
                indexChild = index;
                return false;
            }
        });
        if (indexParent !== - 1 && indexChild !== -1) {
            this.buffer[indexParent][indexChild].status = protocols.RABBITMQ_JOB_STATE_RECEIVED;
            this.buffer[indexParent][indexChild].label = protocols.LABEL_STATE_RECEIVED;
        }
        this.handleCheckInRealTime();
    }

    handleCheckInRealTime() {
        const data = this.getCurrentRequests();
        if (data.length > 0) {
            if (this.realTimer) { this.clearRealTimer(); }
            this.realTimer = setInterval(() => this.checkInRealTime(this.getCurrentRequests()), protocols.TIMER_VUCEM);
        } else {
            this.clearRealTimer();
        }
    }

    checkInRealTime(data) {
        if (data.length > 0) {
            const ids = _.map(data, o => {
                return o.parent.idFactura;
            });

            this.datastageService.getStatusAcuseCove(ids)
                .subscribe(response => {
                    if (response && _.isArray(response)) {
                        const result = _.filter(response, o => {
                            return o.status === protocols.RABBITMQ_JOB_STATE_FAILURE || o.status === protocols.RABBITMQ_JOB_STATE_SUCCESS
                        });
                        if (result.length > 0) {
                            _.forEach(result, o => {
                                this.setNewStatus(o);
                            });
                        }
                    }
                });
        } else {
          this.clearRealTimer();
        }
    }


    setNewStatus(data) {
        let indexParent = -1;
        let indexChild = -1;
        _.forEach(this.buffer, (item, indexP) => {
            const index = _.findIndex(item, o => {
                return o.parent.idFactura === data.idFactura
            });
            if (index !== -1) {
                indexParent = indexP;
                indexChild = index;
                return false;
            }
        });
        if (indexParent !== - 1 && indexChild !== -1) {
            this.buffer[indexParent][indexChild].status = data.status;
            this.buffer[indexParent][indexChild].label = this.getStatus(data.status);
            this.buffer[indexParent][indexChild].parent.path = data.path;
            this.buffer[indexParent][indexChild].parent.status = data.status;
            this.buffer[indexParent][indexChild].parent.errorMessage = data.errorMessage;
        }
    }

    getCurrentRequests() {
        let data = [];
        _.forEach(this.buffer, (item) => {
            const index = _.findIndex(item, o => ( o.status === protocols.RABBITMQ_JOB_STATE_RECEIVED || o.status === protocols.RABBITMQ_JOB_STATE_PROCESSING ));
            if (index !== -1) {
                data.push(item[index]);
            }
        });
        return data.slice();
    }

    clearRealTimer() {
        clearInterval(this.realTimer);
    }
}

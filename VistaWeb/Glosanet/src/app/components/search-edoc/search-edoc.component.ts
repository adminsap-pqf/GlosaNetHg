import { Component, OnInit, AfterViewInit, OnDestroy } from '@angular/core';
import * as _ from 'lodash';
import { DatastageService } from '../../services/datastage/datastage.service';
import { VucemTokenService } from '../../services/vucem-token/vucem-token.service';
import { SessionUser } from '../../services/session/session.service';
import * as Column from '../../model/column.class';
import { ColumnInterface } from '../../interfaces/column.interface';
import * as protocols from '../../util/common.protocols';

import { DEBUG } from '../../settings/app.settings';
import {Catalog} from "../../model/catalog.class";

const EXCEPTION_START_DATE = 'EXCEPTION_START_DATE';
const EXCEPTION_END_DATE = 'EXCEPTION_END_DATE';
const EXCEPTION_SEARCH_BY_PEDIMENTO = 'EXCEPTION_SEARCH_BY_PEDIMENTO';
const EXCEPTION_SEARCH_BY_E_DOC = 'EXCEPTION_SEARCH_BY_E_DOC';
const LINK_TYPE_EDOC = 'LINK_TYPE_EDOC';
const LINK_TYPE_ACUSE_EDOC = 'LINK_TYPE_ACUSE_EDOC';

@Component({
    selector: 'gn-search-edoc',
    templateUrl: './search-edoc.component.html',
    styleUrls: ['./search-edoc.component.scss']
})
export class SearchEdocComponent implements OnInit, AfterViewInit, OnDestroy {

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

    tableHead: any [] = Column.headsE_Doc;
    settings: any [] = Column.settingsE_Doc;

    showTotal = false;
    withoutMessage = 'Ingresa los datos de busqueda para habilitar esta sección';
    vucemTokenActive = 0;

    searchByPedimento = '';
    handleKeySearchPedimento = _.debounce($event => this.changeSearchByPedimento($event), 500);
    searchByEDoc = '';
    handleKeySearchEDoc = _.debounce($event => this.changeSearchByEDoc($event), 500);

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
            this.vucemTokenActive = currentTokenVucem.activo ? 1 : -1;
        } else {
            this.vucemTokenService.getToken(SessionUser.getInstance().getIdClient())
                .subscribe(data => {
                    const { idCliente, lastUpdateDate, pkToken, token, activo } = data;
                    if (idCliente && lastUpdateDate && pkToken && token) {
                        this.vucemTokenActive = activo ? 1 : -1;
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
        if (value.type === LINK_TYPE_EDOC) {
            this.requestEdoc(value.data);
        } else if (value.type === LINK_TYPE_ACUSE_EDOC) {
            this.requestAcuseEdoc(value.data);
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

    changeSearchByEDoc(value: string) {
        this.searchByEDoc = value.trim();
        const path = this.getSearchByEDoc();
        this.processDataForPresentation(path, [EXCEPTION_SEARCH_BY_E_DOC]);
    }

    getSearchByEDoc() {
        if (!this.searchByEDoc) { return ''; }
        return `&searchTerm=${this.searchByEDoc}`;
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

        if (_.indexOf(filterExceptionList, EXCEPTION_SEARCH_BY_E_DOC) === -1) {
            filter += this.getSearchByEDoc();
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
            this.datastageService.getE_Doc(url).subscribe(
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
        const catalog = SessionUser.getInstance().getCatalogVucem();
        _.forEach(collection, o => {
            result.push([
                {
                    ...defaultColumn,
                    label: o.recordIndex,
                },
                {
                    ...defaultColumn,
                    label: o.pedimento,
                },
                {
                    ...defaultColumn,
                    label: o.claveCaso
                },
                {
                    ...defaultColumn,
                    label: o.identificadorCaso ? o.identificadorCaso : 'N/A'
                },
                {
                    ...defaultColumn,
                    label: o.identificadorCaso ? this.getDescription(catalog, o.identificadorCaso.substring(0, 4)) : 'N/A',
                    isDescription: true
                },
                {
                    ...defaultColumn,
                    isStatus: o.claveCaso === 'ED',
                    label: o.claveCaso === 'ED' ? this.getStatus(o.statusEdoc) : 'N/A',
                    errorMessage: o.status === protocols.RABBITMQ_JOB_STATE_FAILURE && o.errorMessageEdoc
                        ? o.errorMessageEdoc
                        : '',
                    parent: o,
                    status: o.statusEdoc,
                    linkType: LINK_TYPE_EDOC
                },
                {
                    ...defaultColumn,
                    isStatus: o.claveCaso === 'ED',
                    label: o.claveCaso === 'ED' ? this.getStatus(o.statusAcuse) : 'N/A',
                    errorMessage: o.status === protocols.RABBITMQ_JOB_STATE_FAILURE && o.errorMessageAcuse
                        ? o.errorMessageAcuse
                        : '',
                    parent: o,
                    status: o.statusAcuse,
                    linkType: LINK_TYPE_ACUSE_EDOC
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

    requestEdoc(data) {
        let term = {};
        if (data.statusEdoc === protocols.RABBITMQ_JOB_STATE_NONE) {
            term = {
                idSolicitud: null,
                idCasoPedimento: data.idCasosPedimento
            };
        } else if (data.statusEdoc === protocols.RABBITMQ_JOB_STATE_FAILURE) {
            term = {
                idSolicitud: data.idSolicitudEdoc,
                casoPedimento: data.identificadorCaso
            };
        }
        this.datastageService.requestEdoc(term).subscribe(
            response => {
                // tslint:disable-next-line:no-unused-expression
                DEBUG && console.log('@requestEdoc: success =>', response);
                this.setStatus(data, LINK_TYPE_EDOC);
            },
            error => {
                // tslint:disable-next-line:no-unused-expression
                DEBUG && console.log('@requestEdoc: error =>', error);
                SessionUser.getInstance().setMessageFailed('en la solicitud');
                document.getElementById('requestFailed').style.display = 'block';
            });
    }

    requestAcuseEdoc(data) {
        SessionUser.getInstance().setLoading(true);
        let term = {};
        if (data.statusAcuse === protocols.RABBITMQ_JOB_STATE_NONE) {
            term = {
                idSolicitud: null,
                idCasoPedimento: data.idCasosPedimento
            };
        } else if (data.statusAcuse === protocols.RABBITMQ_JOB_STATE_FAILURE) {
            term = {
                idSolicitud: data.idSolicitudAcuse,
                casoPedimento: data.identificadorCaso
            };
        }
        this.datastageService.requestAcuseEdoc(term).subscribe(
            response => {
                // tslint:disable-next-line:no-unused-expression
                DEBUG && console.log('@requestAcuseEdoc: success =>', response);
                this.setStatus(data, LINK_TYPE_ACUSE_EDOC);
                SessionUser.getInstance().setLoading(false);
                SessionUser.getInstance().setMessageFailed('');
            },
            error => {
                // tslint:disable-next-line:no-unused-expression
                DEBUG && console.log('@requestAcuseEdoc: error =>', error);
                SessionUser.getInstance().setMessageFailed('en la solicitud');
                document.getElementById('requestFailed').style.display = 'block';
            });
    }

    setStatus(data, linkType) {
        let indexParent = -1;
        let indexChild = -1;
        _.forEach(this.buffer, (item, indexP) => {
            const index = _.findIndex(item, o => {
                return o.parent.idCasosPedimento === data.idCasosPedimento && o.linkType === linkType;
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

    getDescription(catalog: Catalog, key: string) {
        if (catalog && catalog.contenido) {
            const item = _.find(catalog.contenido, o => { return o.clave === key });
            if (!item) { return 'N/A'; }
            return item.nombre;
        }
      return 'N/A';
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
                return o.parent.idCasosPedimento;
            });

            this.datastageService.getStatusAcuseEdoc(ids)
                .subscribe(response => {
                    if (response && _.isArray(response)) {
                        const result = _.filter(response, o => {
                            return o.statusAcuse === protocols.RABBITMQ_JOB_STATE_FAILURE || o.statusAcuse === protocols.RABBITMQ_JOB_STATE_SUCCESS
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
                return o.parent.idCasosPedimento === data.idCasosPedimento && o.linkType === LINK_TYPE_ACUSE_EDOC
            });
            if (index !== -1) {
                indexParent = indexP;
                indexChild = index;
                return false;
            }
        });
        if (indexParent !== - 1 && indexChild !== -1) {
            this.buffer[indexParent][indexChild].status = data.statusAcuse;
            this.buffer[indexParent][indexChild].label = this.getStatus(data.statusAcuse);
            this.buffer[indexParent][indexChild].parent.pathAcuse = data.pathAcuse;
            this.buffer[indexParent][indexChild].parent.status = data.statusAcuse;
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

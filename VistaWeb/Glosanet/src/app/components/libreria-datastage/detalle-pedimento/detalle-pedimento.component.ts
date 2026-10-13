import { Component, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import * as _ from 'lodash';
import { SessionUser } from '../../../services/session/session.service';
import { DatastageService } from '../../../services/datastage/datastage.service';
import { VucemTokenService } from '../../../services/vucem-token/vucem-token.service';
import * as Column from '../../../model/column.class';
import { ColumnInterface } from '../../../interfaces/column.interface';
import {Catalog} from '../../../model/catalog.class';
import * as protocols from '../../../util/common.protocols';

/* Dev Tools */
import { DEBUG } from '../../../settings/app.settings';

const LINK_TYPE_FACTURA = 'LINK_TYPE_FACTURA';
const LINK_TYPE_EDOC = 'LINK_TYPE_EDOC';
const LINK_TYPE_ACUSE_EDOC = 'LINK_TYPE_ACUSE_EDOC';

const TAB_FACTURAS = 'Facturas';
const TAB_E_DOC = 'E-doc';
const TAB_PARTIDAS = 'Partidas';

@Component({
    selector: 'gn-detalle-pedimento',
    templateUrl: './detalle-pedimento.component.html',
    styleUrls: ['./detalle-pedimento.component.scss']
})
export class DetallePedimentoComponent implements OnInit, OnDestroy, AfterViewInit {
    subscription: Subscription;

    isGeneral = false;
    idDataStage = 0;
    dataStageStartDate = null;
    dataStageEndDate: null;
    dataStageFolio = '';
    dataStageTotalValue = 0;
    pedimento = '';
    patente = '';
    seccionAduanera = '';
    comments = '';
    activeTooltip = false;

    activeTab = TAB_FACTURAS;
    optionsTab: string [] = [TAB_FACTURAS, TAB_E_DOC, TAB_PARTIDAS];
    footerTitle = 'Factura';

    placeholder = 'Número de factura';
    searchTerm: string = null;

    grandTotal = 0;
    currentTotal = 0;
    currentPath = '';
    currentPage = 0;

    firstItem = {};
    loading = false;
    buffer: ColumnInterface[][] = [];
    _isMounted = false;

    tableHead: any [] = [];
    settings = [];

    maxWidth = '100%';
    minWidth = '1100px';
    vucemTokenActive = 0; //0 = inexistente, -1 = expirado, 1 = existente y válido

    realTimer: any;

    handleKeySearch = _.debounce($event => this.changeSearchTerm($event), 500);

    constructor(
        private datastageService: DatastageService,
        private vucemTokenService: VucemTokenService,
        private router: Router,
    ) { }

    ngOnInit() {
        this.initialState();
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

    ngAfterViewInit() {
        this._isMounted = true; // TODO: Evitamos llamadas innecesarias de los droplist cuando aun el componente no esta montado
    }

    ngOnDestroy() {
        if (this.subscription) {
            this.subscription.unsubscribe();
        }
        if (this.realTimer) {
            this.clearRealTimer();
        }
    }

    initialState() {
        const storage = localStorage.getItem('currentUser');
        const packedData = storage ? JSON.parse(storage) : {};
        if (storage && packedData.currentPedimento) {
            this.idDataStage = packedData.currentPedimento.idDataStage;
            this.dataStageStartDate = packedData.currentPedimento.startDate;
            this.dataStageEndDate = packedData.currentPedimento.endDate;
            this.dataStageFolio = packedData.currentPedimento.folio;
            this.dataStageTotalValue = packedData.currentPedimento.totalValue;
            this.pedimento = packedData.currentPedimento.pedimento;
            this.patente = packedData.currentPedimento.patente;
            this.seccionAduanera = packedData.currentPedimento.seccionAduanera;
            this.comments = packedData.currentPedimento.comments;
            this.isGeneral = packedData.currentPedimento.idGeneral;
            const activeTab = packedData.currentPedimento.type === 'factura'
                ? TAB_FACTURAS
                : packedData.currentPedimento.type  === 'eDoc'
                    ? TAB_E_DOC
                    : TAB_PARTIDAS;
            this.setActiveTab(activeTab);
        } else {
            this.redirectTo('protected/datastagelibrary');
        }
    }

    redirectTo(path) {
        this.router.navigate([`protected/${path}`]);
    }

    setActiveTab($event) {
        this.activeTab = $event;
        this.footerTitle = $event === TAB_FACTURAS
            ? 'Factura'
            : $event === TAB_PARTIDAS
                ? 'Partida'
                : $event;
        this.searchTerm = '';
        this.grandTotal = 0;
        this.currentTotal = 0;
        this.buffer = [];
        this.clearRealTimer();
        this.setSettings();
        this.reset();
    }

    changeSearchTerm($event) {
        // tslint:disable-next-line:no-unused-expression
        DEBUG && console.log('@handleKeySearch: Entre ', $event);
        this.searchTerm  = $event;
        const path = this.getSearchTerm();
        this.processDataForPresentation(path);
    }

    getSearchTerm() {
        if (!this.searchTerm) { return ''; }
        return `&searchTerm=${this.searchTerm}`;
    }

    processDataForPresentation(filter = '') {
        if (!this._isMounted) { return; }
        this.currentPage = 0;
        this.currentPath = `${filter}`;
        this.reset();
    }

    setSettings() {
        let heads = [];
        let settings = [];
        let placeholder = '';
        let minWidth = '1000px';
        switch (this.activeTab) {
            case TAB_FACTURAS:
                heads = Column.headsFactura;
                placeholder = 'Número de factura';
                settings = Column.settingsFactura;
                minWidth = '750px';
                break;
            case TAB_E_DOC:
                heads = Column.headsE_Doc;
                placeholder = 'Número identificador de caso';
                settings = Column.settingsE_Doc;
                minWidth = '880px';
                break;
            case TAB_PARTIDAS:
                heads = Column.headsPartida;
                placeholder = 'Descripción, Fracción';
                settings = Column.settingsPartida;
                minWidth = '800px';
                break;
        }
        this.tableHead = heads.slice();
        this.settings = settings.slice();
        this.placeholder = placeholder;
        this.minWidth = minWidth;
    }

    handleSingleDownload(value) {
        switch (value.type) {
            case LINK_TYPE_FACTURA:
                this.requestFactura(value.data);
                break;
            case LINK_TYPE_EDOC:
                this.requestEdoc(value.data);
                break;
            case LINK_TYPE_ACUSE_EDOC:
                this.requestAcuseEdoc(value.data);
                break;
        }
    }

    handleCurrentPage(value) {
        this.currentPage = value;
    }

    handleLoading(value) {
        this.loading = value;
    }

    async reset() {
        this.loading = true;
        const data: any = await this.getData(); // Vamos por la primer página
        this.buffer = data.slice();
        this.loading = false;
        this.handleCheckInRealTime();
    }

    getData = (updateData = false) => {
        return new Promise((resolve, reject) => {
            const numItems = Math.floor((window.screen.width - 110) / 225) * 4;
            const size = protocols.PAGING_LIMIT < numItems ? numItems : protocols.PAGING_LIMIT;
            const url = `page=${this.currentPage}&size=${size}${this.currentPath}` +
                `&idDatastage=${this.idDataStage}` +
                `&pedimento=${this.pedimento}`;
            // tslint:disable-next-line:no-unused-expression
            DEBUG && console.log('@getData: URL ' + url);
            const params = {
                option: this.activeTab === TAB_FACTURAS
                    ? 1
                    : this.activeTab === TAB_E_DOC
                        ? 2
                        : 3,
                path: url
            };
            this.datastageService.getDetails(params).subscribe(
                data => {
                    SessionUser.getInstance().setLoading(false);
                    const { results, total } = data;
                    const result: ColumnInterface[][] = this.activeTab === TAB_FACTURAS
                        ? this.patchObjectFacturas(results)
                        : this.activeTab === TAB_E_DOC
                            ? this.patchObjectE_Doc(results)
                            : this.patchObjectPartidas(results);
                    this.grandTotal = this.currentTotal === 0 && total > 0 ? total : this.grandTotal;
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
                    resolve([]);
                    this.loading = false;
                });
        });
    }

    patchObjectFacturas(collection) {
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

    patchObjectE_Doc(collection) {
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
                    errorMessage: o.statusEdoc === protocols.RABBITMQ_JOB_STATE_FAILURE && o.errorMessageEdoc
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
                    errorMessage: o.statusAcuse === protocols.RABBITMQ_JOB_STATE_FAILURE && o.errorMessageAcuse
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

    patchObjectPartidas(collection) {
        const result: ColumnInterface[][] = [];
        const defaultColumn: ColumnInterface = new Column.ColumnClass();
        _.forEach(collection, o => {
            result.push([
                {
                    ...defaultColumn,
                    label: o.recordIndex
                },
                {
                    ...defaultColumn,
                    label: o.fraccion
                },
                {
                    ...defaultColumn,
                    label: o.secuenciaFraccion
                },
                {
                    ...defaultColumn,
                    label: o.subdivisionFraccion
                },
                {
                    ...defaultColumn,
                    label: o.descripcionMercancia
                },
                {
                    ...defaultColumn,
                    label: o.paisOrigenDestino
                },
                {
                    ...defaultColumn,
                    label: o.paisCompradorVendedor
                },
                {
                    ...defaultColumn,
                    isMoney: true,
                    label: o.valorDolares
                },
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
                DEBUG && console.log('@requestFactura: success =>', response);
                this.setStatus(data, 'idFactura', LINK_TYPE_FACTURA);
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

    requestEdoc(data) {
        SessionUser.getInstance().setLoading(true);
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
                this.setStatus(data, 'idCasosPedimento', LINK_TYPE_EDOC);
                SessionUser.getInstance().setLoading(false);
            },
            error => {
                // tslint:disable-next-line:no-unused-expression
                DEBUG && console.log('@requestEdoc: error =>', error);
                SessionUser.getInstance().setLoading(false);
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
                this.setStatus(data, 'idCasosPedimento', LINK_TYPE_ACUSE_EDOC);
                SessionUser.getInstance().setLoading(false);
            },
            error => {
                // tslint:disable-next-line:no-unused-expression
                DEBUG && console.log('@requestAcuseEdoc: error =>', error);
                SessionUser.getInstance().setLoading(false);
                SessionUser.getInstance().setMessageFailed('en la solicitud');
                document.getElementById('requestFailed').style.display = 'block';
            });
    }

    setStatus(data, field, linkType) {
        let indexParent = -1;
        let indexChild = -1;
        _.forEach(this.buffer, (item, indexP) => {
            const index = _.findIndex(item, o => {
                return o.parent[field] === data[field] && o.linkType === linkType;
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
                if (this.activeTab === TAB_FACTURAS) {
                    return o.parent.idFactura;
                } else if (this.activeTab === TAB_E_DOC) {
                    return o.parent.idCasosPedimento;
                }
            });
            if (this.activeTab === TAB_FACTURAS) {
                this.datastageService.getStatusAcuseCove(ids)
                    .subscribe(response => {
                        if (response && _.isArray(response)) {
                            const result = _.filter(response, o => {
                                return o.status === protocols.RABBITMQ_JOB_STATE_FAILURE || o.status === protocols.RABBITMQ_JOB_STATE_SUCCESS
                            });
                            if (result.length > 0) {
                                _.forEach(result, o => {
                                    this.setNewStatus(o, 'idFactura', LINK_TYPE_FACTURA);
                                });
                            }
                        }
                    });
            } else if (this.activeTab === TAB_E_DOC) {
                this.datastageService.getStatusAcuseEdoc(ids)
                    .subscribe(response => {
                        if (response && _.isArray(response)) {
                            const result = _.filter(response, o => {
                                return o.statusAcuse === protocols.RABBITMQ_JOB_STATE_FAILURE || o.statusAcuse === protocols.RABBITMQ_JOB_STATE_SUCCESS
                            });
                            if (result.length > 0) {
                                _.forEach(result, o => {
                                    this.setNewStatus(o, 'idCasosPedimento', LINK_TYPE_ACUSE_EDOC);
                                });
                            }
                        }
                    });
          } else {
              this.clearRealTimer();
          }
        } else {
            this.clearRealTimer();
        }
    }

    setNewStatus(data, field, linkType) {
        let indexParent = -1;
        let indexChild = -1;
        _.forEach(this.buffer, (item, indexP) => {
            const index = _.findIndex(item, o => {
                return o.parent[field] === data[field] && o.linkType === linkType
            });
            if (index !== -1) {
                indexParent = indexP;
                indexChild = index;
                return false;
            }
        });
        if (indexParent !== - 1 && indexChild !== -1) {
            const status = this.activeTab === TAB_FACTURAS ? data.status : data.statusAcuse;
            this.buffer[indexParent][indexChild].status = status;
            this.buffer[indexParent][indexChild].label = this.getStatus(status);
            this.buffer[indexParent][indexChild].parent[this.activeTab === TAB_FACTURAS ? 'path' : 'pathAcuse'] =  this.activeTab === TAB_FACTURAS ? data.path : data.pathAcuse;
            this.buffer[indexParent][indexChild].parent.status = status;
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

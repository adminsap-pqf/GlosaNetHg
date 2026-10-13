import {
    Component, EventEmitter, AfterViewInit, Input, OnInit, OnDestroy,
    Output, OnChanges, SimpleChanges, ViewChild, ElementRef
} from '@angular/core';
import { Router } from '@angular/router';
import { animate, style, transition, trigger } from '@angular/animations';
import { IPageInfo, VirtualScrollerComponent } from 'ngx-virtual-scroller';
import * as _ from 'lodash';
import * as protocols from '../../../util/common.protocols';
import { ColumnInterface } from '../../../interfaces/column.interface';

const LINK_TYPE_FACTURA = 'LINK_TYPE_FACTURA';
const LINK_TYPE_EDOC = 'LINK_TYPE_EDOC';
const LINK_TYPE_ACUSE_EDOC = 'LINK_TYPE_ACUSE_EDOC';

/* Dev Tools */
import { DEBUG } from '../../../settings/app.settings';

@Component({
    selector: 'gn-datastage-table',
    templateUrl: './datastage-table.component.html',
    styleUrls: ['./datastage-table.component.scss'],
    animations: [
        trigger('fadeInOut', [
            transition(':enter', [
                style({opacity: 0}),
                animate(400, style({opacity: 0.7}))
            ]),
            transition(':leave', [
                animate(400, style({opacity: 0}))
            ])
        ])
    ]
})
export class DatastageTableComponent implements OnInit, OnChanges, OnDestroy, AfterViewInit {

    @ViewChild(VirtualScrollerComponent, {static: false})
    private virtualScroller: VirtualScrollerComponent;
    @ViewChild('modalDownload', {static: false}) modalDownload: ElementRef;

    /* Data Controller */
    @Input() buffer: ColumnInterface[][] = [];
    @Input() firstItem = {};
    @Input() grandTotal = 0;
    @Input() currentTotal = 0;
    @Input() currentPage = 0;
    @Input() loading = false;
    /* TODO: Función para obtener las siguientes páginas */
    @Input() getData = null;

    /* Styles */
    @Input() tableHead: any [] = [];
    @Input() minWidth = '100%';
    @Input() maxWidth = '100%';
    @Input() settings = [];

    @Input() showTotal = true;
    @Input() withoutMessage = 'No hay información que mostrar';
    @Input() footerTitle = 'Pedimento';
    @Input() vucemTokenActive = 0;

    @Output() emitLink: EventEmitter<any> = new EventEmitter<any>();
    @Output() emitCurrentPage: EventEmitter<number> = new EventEmitter<number>();
    @Output() emitLoading: EventEmitter<boolean> = new EventEmitter<boolean>();

    scrollItems: ColumnInterface[][] = [];
    timer;
    lodash = _;
    modalIsOpenDownload = false;
    title = 'Error al descargar el documento.';
    subtitle = '¿Deseas volver a descargarlo?';
    selectedItem: ColumnInterface;

    RABBITMQ_JOB_STATE_NONE = protocols.RABBITMQ_JOB_STATE_NONE;
    RABBITMQ_JOB_STATE_RECEIVED = protocols.RABBITMQ_JOB_STATE_RECEIVED;
    RABBITMQ_JOB_STATE_PROCESSING = protocols.RABBITMQ_JOB_STATE_PROCESSING;
    RABBITMQ_JOB_STATE_FAILURE = protocols.RABBITMQ_JOB_STATE_FAILURE;
    RABBITMQ_JOB_STATE_SUCCESS = protocols.RABBITMQ_JOB_STATE_SUCCESS;
    LINK_TYPE_FACTURA = 'LINK_TYPE_FACTURA';
    LINK_TYPE_ACUSE_EDOC = LINK_TYPE_ACUSE_EDOC;

    constructor(private router: Router) { }

    ngOnInit() { }

    ngOnChanges(simple: SimpleChanges) {
        // tslint:disable-next-line:no-unused-expression
        DEBUG && console.log(simple);
    }

    ngAfterViewInit() {
        // TODO: Mover elementos al final de la página (antes de</body>), para que se pueda visualizar sobre todo lo demás
        document.body.appendChild(this.modalDownload.nativeElement);
    }

    ngOnDestroy() {
        // TODO: Eliminar los elementos que fueron  movidos al final
        if (this.modalDownload) {
            this.modalDownload.nativeElement.remove();
        }
    }

    fetchMore(event: IPageInfo) {
        if (event.endIndex !== this.buffer.length - 1) { return; }
        const numItems = Math.floor((window.screen.width - 110) / 225) * 4;
        const size = protocols.PAGING_LIMIT < numItems ? numItems : protocols.PAGING_LIMIT;
        if (event.endIndex !== this.currentTotal - 1 && this.currentTotal > 0) {
            const totalPages = this.currentTotal >= size ?  Math.ceil(this.currentTotal / size) : 0;
            if ( this.currentPage > totalPages) {
                this.emitCurrentPage.emit(totalPages);
                return;
            }
            if (event.startIndex !== 0) { this.emitLoading.emit(true); }
            this.emitCurrentPage.emit(this.currentPage + 1);
            this.fetchNextChunk(this.buffer.length, size)
                .then(chunk => {
                    this.buffer = this.buffer.concat(chunk);
                    this.emitLoading.emit(false);
                }, () => {
                    this.emitLoading.emit(false);
                });
        }
    }

    fetchNextChunk(skip: number, limit: number): Promise<any[]> {
        // tslint:disable-next-line:no-unused-expression
        DEBUG && console.log('@fetchNextChunk: Entre, skip => ' + skip + '  ||  limit => ' + limit);
        return new Promise((resolve, reject) => {
            clearTimeout(this.timer);
            this.timer = setTimeout( async() => {
                if (skip < this.grandTotal) {
                    const data: any = await this.getData(true);
                    this.emitLoading.emit(false);
                    return resolve(data);
                }
                this.emitLoading.emit(false);
                reject();
            }, 200);
        });
    }

    scrollTo() {
        this.virtualScroller.scrollInto(this.firstItem);
    }

    onLink(item) {
        if (item.parent && !_.isEmpty(item.parent)) {
            this.emitLink.emit({
                type: item.linkType,
                data: item.parent
            });
        }
    }

    openModal(item: ColumnInterface) {
        this.modalIsOpenDownload = true;
        if (!this.vucemTokenActive) {
            this.title = 'Token no encontrado';
            this.subtitle = 'Es necesario registrar un token';
        } else if (this.vucemTokenActive === 0) {
            this.title = 'Token no encontrado';
            this.subtitle = 'Es necesario registrar un token';
        } else if (this.vucemTokenActive === -1) {
            this.title = 'Token expirado';
            this.subtitle = 'Es necesario registrar un token vigente';
        } else if (item.status === this.RABBITMQ_JOB_STATE_NONE) {
            this.title = '¿Deseas solicitar la descargar del documento?' + (item.linkType === LINK_TYPE_EDOC ? ' Descarga habilitada en 24 horas' : '');
            this.subtitle = '';
        } else if (item.status === this.RABBITMQ_JOB_STATE_FAILURE) {
            this.title = 'Error al descargar el documento.';
            this.subtitle = '¿Deseas volver a descargarlo?'+ (item.linkType === LINK_TYPE_EDOC ? ' Descarga habilitada en 24 horas' : '');
        }
        this.selectedItem = item;
    }

    closeModal() {
        this.modalIsOpenDownload = false;
    }

    handleDownload(item: any) {
        if (!this.vucemTokenActive) {
            // TODO: No hay token, redireccionamos a agregar token
            this.redirectToToken();
        } else if (this.vucemTokenActive === 0 || this.vucemTokenActive === -1) {
            this.redirectToToken();
        } else if (this.selectedItem && ( this.selectedItem.status === this.RABBITMQ_JOB_STATE_NONE
                    || this.selectedItem.status === this.RABBITMQ_JOB_STATE_FAILURE)) {
            // TODO: Solicitar descarga manual de archivos
            this.onLink(this.selectedItem);
        } else if (item.status === this.RABBITMQ_JOB_STATE_SUCCESS) {
            const currentPath = item.linkType === LINK_TYPE_FACTURA
                ? item.parent.path
                : item.linkType === LINK_TYPE_EDOC
                    ? item.parent.pathEdoc
                    : item.linkType === LINK_TYPE_ACUSE_EDOC
                        ? item.parent.pathAcuse
                        : null;
            if (currentPath) {
                let path = '';
                if (currentPath.indexOf('C:') !== -1) {
                    // TODO: Path de desarrollo
                    const format = currentPath.split('\\').join('/');
                    path = '/VUCEM_downloads/' + format.slice(22, currentPath.length);
                } else {
                    path = currentPath.slice(23, currentPath.length);
                }
                this.downloadFile(path);
            }
        }
        this.closeModal();
    }

    redirectToToken() {
        this.router.navigate(['protected/token']);
    }

    downloadFile(path) {
        const filename = path.substring(path.lastIndexOf('/') + 1);
        const element = document.createElement('a');
        element.setAttribute('href', protocols.ROUTE_FILES + path);

        /*
         * TODO: Se omite la descarga para mostrar siempre en una nueva pestaña el documento.
         * element.setAttribute('download', filename);
         */

        element.setAttribute('target', '_blank');
        element.style.display = 'none';
        document.body.appendChild(element);
        element.click();
        document.body.removeChild(element);
    }
}

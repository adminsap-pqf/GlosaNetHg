import { Component, AfterViewInit, OnDestroy, ElementRef, OnInit, ViewChild } from '@angular/core';
import { trigger, style, animate, transition } from '@angular/animations';
import { VucemTokenService } from '../../services/vucem-token/vucem-token.service';
import { SessionUser } from '../../services/session/session.service';
import { VucemTokenClass } from '../../model/vucem-token.class';
import _ from 'lodash';

/* Dev Tools */
import { DEBUG } from '../../settings/app.settings';

@Component({
    selector: 'gn-vucem-token',
    templateUrl: './vucem-token.component.html',
    styleUrls: ['./vucem-token.component.scss'],
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
export class VucemTokenComponent implements OnInit, AfterViewInit, OnDestroy {

    @ViewChild('modalAddToken', {static: false}) modalAddToken: ElementRef;
    @ViewChild('modalDeleteToken', {static: false}) modalDeleteToken: ElementRef;

    modalIsOpenAdd = false;
    modalIsOpenDelete = false;
    withToken = false;
    mode = '';
    currentToken = new VucemTokenClass();
    inputToken = '';
    placeholder = 'Escribe Aquí';

    constructor(private vucemTokenService: VucemTokenService) { }

    ngOnInit() {
        const currentTokenVucem = SessionUser.getInstance().getVucemToken();
        if (currentTokenVucem) {
            this.currentToken = {
                idToken: currentTokenVucem.idToken,
                token: currentTokenVucem.token,
                lastUpdateDate: currentTokenVucem.lastUpdateDate,
                activo: currentTokenVucem.activo
            };
            this.withToken = true;
        } else {
            SessionUser.getInstance().setLoading(true);
            this.vucemTokenService.getToken(SessionUser.getInstance().getIdClient())
                .subscribe(data => {
                    // tslint:disable-next-line:no-unused-expression
                    DEBUG && console.log('@ngOnInit: data => ', data);
                    const { idCliente, lastUpdateDate, pkToken, token, activo } = data;
                    if (idCliente && lastUpdateDate && pkToken && token) {
                        this.currentToken = {
                            idToken: pkToken,
                            token: token,
                            lastUpdateDate: lastUpdateDate,
                            activo: activo
                        };
                        this.withToken = true;
                        SessionUser.getInstance().setVucemToken(this.currentToken);
                    }
                    SessionUser.getInstance().setLoading(false);
                }, error => {
                    // tslint:disable-next-line:no-unused-expression
                    DEBUG && console.log('Error', error);
                    SessionUser.getInstance().setLoading(false);
                });
        }
    }

    ngAfterViewInit() {
        // TODO: Mover elementos al final de la página (antes de</body>), para que se pueda visualizar sobre todo lo demás
        document.body.appendChild(this.modalAddToken.nativeElement);
        document.body.appendChild(this.modalDeleteToken.nativeElement);
    }

    ngOnDestroy() {
        // TODO: Eliminar los elementos que fueron  movidos al final
        if (this.modalAddToken) {
            this.modalAddToken.nativeElement.remove();
        }
        if (this.modalDeleteToken) {
            this.modalDeleteToken.nativeElement.remove();
        }
    }

    handleInput(value: string) {
        this.inputToken = value.trim();
    }

    openModalAddToken(mode) {
        this.mode = mode;
        this.inputToken = '';
        if (mode === 'add') {
            this.placeholder = 'Escribe Aquí';
        } else {
            this.placeholder = this.currentToken.token;
        }
        this.modalIsOpenAdd = true;
    }

    closeModalAddToken() {
        this.modalIsOpenAdd = false;
    }

    handleSave() {
        this.closeModalAddToken();
        SessionUser.getInstance().setLoading(true);
        const term = {
            token: this.inputToken,
            idCliente: SessionUser.getInstance().getIdClient()
        };
        this.vucemTokenService.addToken(term).subscribe(
            data => {
                // tslint:disable-next-line:no-unused-expression
                DEBUG && console.log('@handleSave: success => ', data);
                SessionUser.getInstance().setLoading(false);
                if (!_.isEmpty(data)) {
                    this.currentToken = {
                        idToken: data.pkToken,
                        token: data.token,
                        lastUpdateDate: data.lastUpdateDate,
                        activo: true
                    };
                    SessionUser.getInstance().setVucemToken(this.currentToken);
                    this.withToken = true;
                } else {
                    SessionUser.getInstance().setMessageFailed('al guardar token');
                    document.getElementById('requestFailed').style.display = 'block';
                }
            },
            error => {
                // tslint:disable-next-line:no-unused-expression
                DEBUG && console.log('@handleSave: error =>', error);
                SessionUser.getInstance().setLoading(false);
                SessionUser.getInstance().setMessageFailed('al guardar token');
                document.getElementById('requestFailed').style.display = 'block';
            }
        );
    }

    handleUpdate() {
        this.closeModalAddToken();
        SessionUser.getInstance().setLoading(true);
        const term = {
            pkToken: this.currentToken.idToken,
            token: this.inputToken
        };
        this.vucemTokenService.updateToken(term).subscribe(
            data => {
                // tslint:disable-next-line:no-unused-expression
                DEBUG && console.log('@handleUpdate: success => ', data);
                SessionUser.getInstance().setLoading(false);
                if (!_.isEmpty(data)) {
                    this.currentToken = {
                        idToken: data.pkToken,
                        token: data.token,
                        lastUpdateDate: data.lastUpdateDate,
                        activo: true
                    };
                    SessionUser.getInstance().setVucemToken(this.currentToken);
                    this.withToken = true;
                } else {
                    SessionUser.getInstance().setMessageFailed('al guardar token');
                    document.getElementById('requestFailed').style.display = 'block';
                }
            },
            error => {
                // tslint:disable-next-line:no-unused-expression
                DEBUG && console.log('@handleUpdate: error =>', error);
                SessionUser.getInstance().setLoading(false);
                SessionUser.getInstance().setMessageFailed('al actualizar token');
                document.getElementById('requestFailed').style.display = 'block';
            }
        );
    }

    openModalDeleteToken($event) {
        if (!_.isEmpty($event)) {
            $event.stopPropagation();
        }
        this.modalIsOpenDelete = true;
    }

    closeModalDeleteToken() {
        this.modalIsOpenDelete = false;
    }

    handeleDelete() {
        this.closeModalDeleteToken();
        SessionUser.getInstance().setLoading(false);
        this.vucemTokenService.deleteToken(this.currentToken.idToken)
            .subscribe((data: VucemTokenClass) => {
                // tslint:disable-next-line:no-unused-expression
                DEBUG && console.log('@handeleDelete: success => ', data);
                this.currentToken = new VucemTokenClass();
                this.withToken = false;
                SessionUser.getInstance().setLoading(false);
            }, error => {
                // tslint:disable-next-line:no-unused-expression
                DEBUG && console.log('@handeleDelete: error => ', error);
                SessionUser.getInstance().setLoading(false);
                SessionUser.getInstance().setMessageFailed('al eliminar token');
                document.getElementById('requestFailed').style.display = 'block';
            });
    }
}

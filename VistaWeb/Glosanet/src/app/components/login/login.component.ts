import {Component, OnInit} from '@angular/core';
import {Usuario} from '../../model/usuario.class';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user/user.service';
import {Observable, forkJoin, of} from "rxjs";
import 'rxjs/add/operator/switchMap';
import {ActivatedRoute, Router} from '@angular/router';
import "rxjs-compat/add/observable/empty";
import {catchError, map, switchMap} from "rxjs/operators";
import {DEBUG} from "../../settings/app.settings";
import {Parametros} from "../../model/parametros.class";
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {CatalogoService} from "../../services/catalogo/catalogo.service";
import {Catalog} from "../../model/catalog.class";

@Component({
  selector: 'gn-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  usuario: string = '';
  password: string = 'normal';
  loginFailed: boolean = false;
  returnUrl: string;
  /* Estructura del form */
  loginForm: FormGroup;
  /* Deshabilitar botón cuando se realiza la petición */
  isLoading: boolean = false;

  constructor(private userInfoService: UserService,
              private authService: AuthenticationService,
              private catalogoService: CatalogoService,
              private route: ActivatedRoute,
              private router: Router) {

    // redirect to home if already logged in
    if (this.authService.currentUserValue) {
      this.router.navigate(['/protected/']);
    }
  }

  ngOnInit() {

    this.loginForm = new FormGroup({
      'usuario': new FormControl(this.usuario, [Validators.required, Validators.minLength(1)]),
      'password': new FormControl('', [Validators.required, Validators.minLength(1)])
    });

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/protected/';
  }

  change() {
    this.password = 'normal';
  }

  onSubmit(user: Usuario) {

    DEBUG && console.log("@onSubmit: inicio");
    /*
      Haremos el proceso de autenticación en secuencia:
      1. Primero obtendremos el token de acceso y el id numerico del usuario
      2. Con esa información obtendremos la información del usuario
      3. Traeremos finalmente los permisos del usuario y los usuarios de la empresa
    */

    //Deshabilitamos botón de entrar
    this.isLoading = true;
    this.loginFailed = false;

    //Primer request: Autenticación y obtención de tokens
    const requestsSequence = this.authService.login(user.usuario, user.password)
      .pipe(
        switchMap(loginData => {
          if (loginData) {
            //Segundo request: Obtención de información del usuario
            return this.userInfoService.obtenerUsuarioActualConToken(loginData.token)
              .pipe(
                switchMap(userInfo => {
                  if (userInfo) {
                    let param = new Parametros();
                    param.id = userInfo.cliente.idCliente;
                    //Tercer request: Traemos usuarios del cliente y sus permisos
                    let promises = [];
                    promises.push(this.userInfoService.buscarUsuariosPorEmpresaConToken(loginData.token, param));
                    promises.push(this.catalogoService.getCatalog(loginData.token, [1]));
                    return forkJoin(promises).pipe(
                      map((response: any) => {
                        return {
                          loginData: loginData,
                          userInfo: userInfo,
                          adminData: response[0],
                          catalogVucem: response[1].length > 0 ? response[1][0] : new Catalog()
                        }
                      }),
                      catchError(() => of(Observable.empty()))
                    );
                    /*
                    TODO: Pedir solo lista de usuarios
                    return this.userInfoService.buscarUsuariosPorEmpresaConToken(loginData.token, param).pipe(
                      map(adminData => {
                        return {
                          loginData: loginData,
                          userInfo: userInfo,
                          adminData: adminData
                        }
                      })
                    );*/
                  }
                  //Sin User info
                  return Observable.empty();
                }));
          }
          //Sin loginData
          return Observable.empty();
        })
      );

    requestsSequence.subscribe(
      (authData: any) => {
        DEBUG && console.log("@onSubmit: POST call successful value returned in body",
          authData);
        if (authData.hasOwnProperty('loginData')) {
          //Guardamos el tiempo valido de la sesión (la hora en que caducará el token)
          let endTime = new Date();
          endTime.setMilliseconds(7230000); // MEDIDA PRODUCCION: Considerar que el token caduca en dos horas y más 30 segundos de vida
          // endTime.setMilliseconds(150000); // MEDIDA DEV: Pruebas tiempo 2:30 min
          authData['loginData'].endTime = endTime;
        }

        //Guardamos información del usuario en localStorage
        if (typeof(Storage) !== "undefined") {
          localStorage.setItem('currentUser', JSON.stringify(authData));
        }
        //Guardamos información del usuario en memoria local
        this.authService.currentUserValue = authData;
        //this.authService.packUserSessionData(authData);

        DEBUG && console.log("@onSubmit: redirect");

        //autenticación exitosa, extramos...
        this.router.navigate([this.returnUrl]);
      },
      response => {
        DEBUG && console.log("@onSubmit: POST call in error", response);
        this.isLoading = false;
        this.loginFailed = true;
      },
      () => {
        DEBUG && console.log("@onSubmit: The POST observable is now completed.");
        this.isLoading = false;
      }
    );
  }

}

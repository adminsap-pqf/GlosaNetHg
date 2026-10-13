import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {DEBUG} from "../../settings/app.settings";
import {AuthenticationService} from "../authentication/authentication.service";
import {Router} from "@angular/router";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private router: Router, private authenticationService: AuthenticationService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    DEBUG && console.log("@intercept: inicio error.interceptor");
    return next.handle(request).pipe(catchError(err => {

      if (err.status === 401 || err.status === 403) {
        // auto logout if 401 or 403 response returned from api (unauthorized or forbidden)
        /*this.authenticationService.logout();*/

        //redirect to login page with the return url
        /*this.router.navigate(['/']);
        return null;*/
      }

      const error = err.error.message || err.statusText;
      return throwError(error);
    }));
  }
}

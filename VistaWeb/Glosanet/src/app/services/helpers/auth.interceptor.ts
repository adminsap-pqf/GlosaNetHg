import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {AuthenticationService} from "../authentication/authentication.service";
import {DEBUG} from "../../settings/app.settings";
import {mergeMap} from 'rxjs/operators';
import {AuthenticatedUser} from "../../model/authenticatedUser.class";
import {getHeadersGet, getHeadersPost} from "./apiHeaders.helper";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authenticationService: AuthenticationService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    DEBUG && console.log("@intercept: inicio auth.interceptor");

    //request props
    const {method, headers} = request;

    return of(null)
      .pipe(mergeMap(() => {
        return handleRoute(this.authenticationService.currentUserValue);
      }));

    function handleRoute(currentUserVal: AuthenticatedUser) {
      let shouldSetHeaders: boolean = false;
      if (currentUserVal && currentUserVal.loginData && currentUserVal.loginData.token
        && !headers.get('x-auth-token'))
        shouldSetHeaders = true;

      switch (true) {
        case method === 'POST':
          // add authorization header with  token if available
          if (shouldSetHeaders)
            request = request.clone({headers: getHeadersPost()});

          break;
        case method === 'GET':
          // add authorization header with  token if available
          if (shouldSetHeaders)
            request = request.clone({headers: getHeadersGet()});

          break;
        default:
        // pass through any requests not handled above
      }

      return next.handle(request);
    }

  }

}

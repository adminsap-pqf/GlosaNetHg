import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthenticationService} from '../services/authentication/authentication.service';
import {SessionUser} from '../services/session/session.service';

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

    // tslint:disable-next-line:comment-format
    //1. Check if currentUser is still active
    const currentUser = this.authenticationService.currentUserValue;
    if (currentUser) {
      // user info in storage and authorised: reset user information in memory and return true
      if (!SessionUser.getInstance().getToken()) {
        // tslint:disable-next-line:comment-format
        //sin token; restablece info de usuario
        this.authenticationService.packUserSessionData(currentUser);
      }
      return true;
    }

    // not logged in so redirect to login page with the return url
    this.router.navigate(['/'], {queryParams: {returnUrl: state.url}});
    return false;
  }
}

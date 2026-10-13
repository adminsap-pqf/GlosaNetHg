import {HttpHeaders} from "@angular/common/http";
import {SessionUser} from "../session/session.service";

export const getHeadersPost = () => {
  return new HttpHeaders()
    .set('Content-Type', 'application/json')
    .set('x-auth-token', SessionUser.getInstance().getToken());
}

export const getHeadersGet = () => {
  return new HttpHeaders()
    .set('x-auth-token', SessionUser.getInstance().getToken());
}

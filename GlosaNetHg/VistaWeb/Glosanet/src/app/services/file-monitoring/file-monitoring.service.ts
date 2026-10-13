import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { SessionUser } from '../session/session.service';
import { Subject } from 'rxjs';

@Injectable()
export class FileMonitoringService {

  private url = SessionUser.getInstance().getIP() + 'controller/jobs/';
  public reloadFileMonitoring = new Subject<any>();

  constructor(private http: HttpClient) { }

  handleRealoadFileMonitoring(currentURL: string) {
    this.reloadFileMonitoring.next(currentURL);
  }

  getDataStagePhase1(path) {
    return this.http.get<any>(`${this.url}datastagePhase1?${path}`);
  }

  getStackTrace(guid) {
    const headers = new HttpHeaders()
      .set('Content-Type', 'text/plain; charset=utf-8')
      .set('x-auth-token', SessionUser.getInstance().getToken());

    return this.http.get<any>(`${this.url}datastagePhase1/${guid}/stacktrace`, {headers, responseType: "text" as "json"});
  }
}

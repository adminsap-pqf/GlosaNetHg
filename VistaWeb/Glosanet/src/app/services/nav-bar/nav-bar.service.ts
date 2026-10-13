import {Subject} from 'rxjs';
import {Injectable} from '@angular/core';

@Injectable()
export class NavBarService {
  // Observable source
  private closeNavSource = new Subject<boolean>();

  constructor() {
  }

  // Service message commands
  closeNavBar(mission: boolean) {
    this.closeNavSource.next(mission);
  }

}

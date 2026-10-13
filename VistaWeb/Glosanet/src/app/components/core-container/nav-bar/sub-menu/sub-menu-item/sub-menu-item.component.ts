import { error } from 'util';
import { Observable } from 'rxjs';
import { NavBarService } from '../../../../../services/nav-bar/nav-bar.service';
import { NavItem } from './../../nav-item.interface';
import { Router } from '@angular/router';
import { Component, OnInit, Input } from '@angular/core';
import { NavBarComponent } from '../../nav-bar.component';

/* Dev Tools */
import { DEBUG } from "../../../../../settings/app.settings";

@Component({
  selector: 'gn-sub-menu-item',
  templateUrl: './sub-menu-item.component.html',
  styleUrls: ['./sub-menu-item.component.scss']
})
export class SubMenuItemComponent implements OnInit {

  @Input() item: Object;
  activeRouteClass = '';
  value = false;
  constructor(private router: Router, private navbar: NavBarService, private navService: NavBarService, private navBar: NavBarComponent) { }
  ngOnInit() {
    this.isRouteActive();
    DEBUG && console.log(this.item);
  }
  navigateToRoute() {
    let castedItem = this.item as NavItem;
    this.navService.closeNavBar(true);
    this.navBar.closeNav();
    this.router.navigate([castedItem.key.toString()]);
  }

  isRouteActive() {
    let castedItem = this.item as NavItem;
    if (this.router.url === `/${castedItem.key}`) {
      this.activeRouteClass = 'active';
    }
  }

}

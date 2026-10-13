import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { NavBarService } from './../../../../services/nav-bar/nav-bar.service';

@Component({
  selector: 'gn-sub-menu',
  templateUrl: './sub-menu.component.html',
  styleUrls: ['./sub-menu.component.scss']
})
export class SubMenuComponent implements OnInit {

  @Input() subMenuItems: Array<Object> = [];
  @Input() title = '';
  @Input() titleIcon: string;

  public isVendedor = false;
  public clsBntAddRequirment = 'btn';

  constructor(private router: Router, private navbarService: NavBarService) { }

  ngOnInit() {
    /* this.isVendedor = (this.title === 'nav_pendingsVendedor') ? true : false; */
  }

  showActiveOption() {

  }

  /* addRequirment() {
    this.navbarService.closeNavBar(true);
    this.router.navigate(['/protected/newRequest']);
  } */

}

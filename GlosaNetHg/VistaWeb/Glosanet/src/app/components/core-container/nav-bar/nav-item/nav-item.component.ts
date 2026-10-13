import { Router } from '@angular/router';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { NavItem } from './../nav-item.interface';
import { NavBarComponent } from '../nav-bar.component';

@Component({
  selector: 'gn-nav-item',
  templateUrl: './nav-item.component.html',
  styleUrls: ['./nav-item.component.scss']
})
export class NavItemComponent implements OnInit {
  itemIcon: string;
  itemName: string;
  activeRouteClass = '';

  @Input() item: NavItem;
  @Input() active: boolean;
  @Input() navSize;
  @Input() section: boolean;

  @Output() onItemSelected: EventEmitter<Object> = new EventEmitter<Object>();

  constructor(private router: Router, private navBar: NavBarComponent) {}

  ngOnInit() {
    this.itemIcon = this.item.iconPath;
    this.itemName = this.item.translatedName;
    //this.openItemSubMenu();
  }

  isRouteActive() {
    const castedItem = this.item as NavItem;
    if (this.router.url === `/${castedItem.key}`) {
      this.activeRouteClass = 'active';
    }
  }

  openItemSubMenu() {
     this.onItemSelected.emit({ key: this.item.key, submenu: this.item.subMenuComponent });
   
   
   
    /* const castedItem = this.item as NavItem; */
   
   
   
   //this.navService.closeNavBar(true);
   
   
   
   
   /* this.router.navigate([castedItem.key.toString()]); */
  }

  open(){
    this.onItemSelected.emit({ key: this.item.key, submenu: this.item.subMenuComponent });
  }
}

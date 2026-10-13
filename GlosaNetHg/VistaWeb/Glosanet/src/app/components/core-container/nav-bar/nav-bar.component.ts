import {NavBarService} from '../../../services/nav-bar/nav-bar.service';
import {BrowserDetectionService} from '../../../services/browser-detection/browser-detection.service';
import {SubMenuComponent} from './sub-menu/sub-menu.component';
import {
  Component,
  ComponentFactoryResolver,
  ElementRef,
  EventEmitter,
  Input,
  OnInit,
  Output,
  Renderer,
  ViewChild,
  ViewContainerRef
} from '@angular/core';
import {Router} from '@angular/router';
import {NavItem} from './nav-item.interface';
import _ from 'lodash';
import {SessionUser} from '../../../services/session/session.service';
/* Dev Tools */
import {DEBUG} from "../../../settings/app.settings";

@Component({
  selector: 'gn-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit {

  @Input() navItems: Array<NavItem>;
  @Output() navBarStatusChanged: EventEmitter<Object> = new EventEmitter<Object>();
  @ViewChild('target', {read: ViewContainerRef, static: false}) componentContainer: ViewContainerRef;


  /* Imágenes */
  public logoGlosanet = './assets/images/logo_glosa.svg';
  public classTxtLogo = 'txtLogoHide';

  initNav: boolean = false;

  /* Clases dinámicas de SCSS */
  navSize = 'closed';
  auxBarSize = 'closed';
  auxBarType = '';
  logoClass = '';
  browserAnimationClass = '';

  /* Varibales para funcionamiento general */
  activeOption = {key: 'home'};

  itemActual: any;

  block: boolean = false;

  clickListener: Function;

  constructor(private componentFactoryResolver: ComponentFactoryResolver,
              private browserService: BrowserDetectionService,
              private navService: NavBarService,
              private router: Router,
              private renderer: Renderer,
              private elementRef: ElementRef
  ) {
    this.clickListener = renderer.listenGlobal(
      'document',
      'click',
      (event: MouseEvent) => this.handleGlobalClick(event)
    );
  }


  ngOnInit() {
    // this.getBrowserAnimationClass();
    // this.navService.closeNav$.subscribe(value => { this.closeNav(); });
    DEBUG && console.log(this.navItems);
    setTimeout(() => {
      this.initNav = true;
      this.navSize = 'peek';

    }, 700);
  }

  ngOnChanges() {
  }

  ngAfterViewInit() {
  }

  ngDoCheck() {
    if (SessionUser.getInstance().getLoading() || SessionUser.getInstance().getConfirm()) {
      this.block = true;
    } else {
      this.block = false;
    }
  }

  ngOnDestroy() {
    try {
      this.componentContainer.clear();
    } catch (error) {
      DEBUG && console.log(error);
    }

  }

  changeNavSize(index?) {
    if (index !== undefined) {
      let eventStatus = 'close';
      this.navSize = 'opened';
      // this.logoPetroflex = './assets/images/logo_petroflex_txt_white.svg';
      this.classTxtLogo = 'txtLogo';
      this.logoClass = 'animated slideInLeft';
      eventStatus = 'open';
    } else {
      let eventStatus = 'close';

      if (this.navSize === 'opened') {
        this.navSize = 'peek';
        this.logoGlosanet = './assets/images/logo_glosa.svg';
        this.classTxtLogo = 'txtLogoHide';
        this.logoClass = 'animated slideInRight';
        eventStatus = 'close';
      } else {
        this.navSize = 'opened';
        // this.logoPetroflex = './assets/images/logo_petroflex_txt_white.svg';
        this.classTxtLogo = 'txtLogo';
        this.logoClass = 'animated slideInLeft';
        eventStatus = 'open';
      }

      this.navBarStatusChanged.emit({state: this.navSize, menuEvent: eventStatus});
    }
  }

  closeNav() {
    const eventStatus = 'close';

    //setTimeout(this.componentContainer.clear, 800);
    this.navSize = 'peek';
    this.logoGlosanet = './assets/images/logo_glosa.svg';
    this.logoClass = 'animated slideInRight';
    this.auxBarSize = 'closed';
    this.classTxtLogo = 'txtLogoHide';
    this.navBarStatusChanged.emit({state: this.navSize, menuEvent: eventStatus});

  }

  changeAuxBarState($event) {
    DEBUG && console.log($event);
    DEBUG && console.log(this.itemActual);
    DEBUG && console.log(this.auxBarSize);
    if (this.auxBarSize === 'opened') {
      if ($event.submenu === 'menu') {
        if (this.itemActual === $event.key) {
          this.auxBarSize = 'closed';
        } else {
          const itemData = _.find(this.navItems, {'key': $event.key});
          this.itemActual = itemData.key;
          this.changeAuxBarComponent(itemData);
        }
      }
      this.componentContainer.clear();
    } else {
      this.itemActual = $event.key;
      this.auxBarSize = 'opened';
      this.activeOption = $event;
      this.componentContainer.clear();
    }
  }

  private changeAuxBarComponent(item: NavItem) {
    const factory = this.componentFactoryResolver.resolveComponentFactory(SubMenuComponent);
    const ref = this.componentContainer.createComponent(factory);
    const instance = ref.instance;
    instance.subMenuItems = item.subMenuItems;
    instance.title = item.subMenuTitle;
    instance.titleIcon = item.subMenuIcon;
    ref.changeDetectorRef.detectChanges();
  }

  /* getBrowserAnimationClass() {
    let browserObject = this.browserService.whichBrowserImUsing();

    switch (browserObject.name) {
      case 'Internet Explorer':
      case 'Microsoft Edge':
        this.browserAnimationClass = 'explorer';
        break;
      default:
        this.browserAnimationClass = '';
    }
  } */

  goHome() {
    this.navService.closeNavBar(true);
    this.router.navigate(['/protected/']);
  }

  closeBar() {
    this.navService.closeNavBar(true);
  }


  /* Escucha el evento click globalmente */
  handleGlobalClick(event: MouseEvent): void {
    const withinElement = this.elementRef.nativeElement.contains(event.target);
    if (!this.elementRef.nativeElement.contains(event.target)) {
      this.closeNav();
    }
  }

}

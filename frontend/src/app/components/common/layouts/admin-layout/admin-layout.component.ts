import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { Subscription } from "rxjs/Subscription";
import { MdSidenav } from '@angular/material';
import { TranslateService } from 'ng2-translate/ng2-translate';
import { ThemeService } from '../../../../services/theme/theme.service';
import * as Ps from 'perfect-scrollbar';
import * as domHelper from '../../../../helpers/dom.helper';

@Component({
  selector: 'app-admin-layout',
  templateUrl: './admin-layout.template.html'
})
export class AdminLayoutComponent implements OnInit {
  private isMobile;
  screenSizeWatcher: Subscription;
  isSidenavOpen: Boolean = false;
  url;
  @ViewChild(MdSidenav) private sideNave: MdSidenav;

  constructor(
    private router: Router,
    public translate: TranslateService,
    public themeService: ThemeService
  ) {
    // Close sidenav after route change in mobile
    router.events.filter(event => event instanceof NavigationEnd).subscribe((routeChange: NavigationEnd) => {
      this.url = routeChange.url;
      if(this.isNavOver()) {
        this.sideNave.close()
      }
    });
    
    // Translator init
    const browserLang: string = translate.getBrowserLang();
    translate.use(browserLang.match(/en|fr/) ? browserLang : 'en');
  }
  ngOnInit() {
    // Initialize Perfect scrollbar for sidenav
    let navigationHold = document.getElementById('scroll-area');
    Ps.initialize(navigationHold, {
      suppressScrollX: true
    });
    console.log(this.themeService.activatedThemeName);
  }
  isNavOver() {
    return window.matchMedia(`(max-width: 960px)`).matches;
  }
}
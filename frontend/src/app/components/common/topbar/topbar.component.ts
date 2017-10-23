import { Component, Input } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { MatIconRegistry } from '@angular/material';

@Component({
    selector: 'topbar',
    templateUrl: './topbar.component.html'
})

export class TopbarComponent {

    @Input() sidenav;

    constructor (iconRegistry: MatIconRegistry, sanitizer: DomSanitizer) {
        iconRegistry.addSvgIcon(
            'toggle',
            sanitizer.bypassSecurityTrustResourceUrl('assets/images/menu-white.svg')
        );
    }

    public toggleSidenav () {
        this.sidenav.toggle();
    }

}

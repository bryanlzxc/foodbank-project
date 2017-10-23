import { Component } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { MatIconRegistry } from '@angular/material';

@Component({
    selector: 'navigation',
    templateUrl: './navigation.component.html',
    styleUrls: [ './navigation.component.css' ]
})

export class NavigationComponent {

    public pages = [
        {
            "name": "Dashboard"
        },
        {
            "name": "Inventory"
        },
        {
            "name": "Allocation"
        },
        {
            "name": "Packing List"
        }
    ]

    constructor (iconRegistry: MatIconRegistry, sanitizer: DomSanitizer) {
        iconRegistry.addSvgIcon('Dashboard', sanitizer.bypassSecurityTrustResourceUrl('assets/images/dashboard.svg'));
        iconRegistry.addSvgIcon('Inventory', sanitizer.bypassSecurityTrustResourceUrl('assets/images/inventory.svg'));
        iconRegistry.addSvgIcon('Allocation', sanitizer.bypassSecurityTrustResourceUrl('assets/images/allocation.svg'));
        iconRegistry.addSvgIcon('Packing List', sanitizer.bypassSecurityTrustResourceUrl('assets/images/packlist.svg'));
    }

}

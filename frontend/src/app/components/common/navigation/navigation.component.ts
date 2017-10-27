import { Component }    from '@angular/core';
import navbarRoutes     from './../../../../config/routes';

const userType = 'beneficiary';

@Component({
    selector: 'navigation',
    templateUrl: './navigation.component.html',
    styleUrls: [ './navigation.component.css' ]
})

export class NavigationComponent {

    public pages;

    constructor () {
        this.pages = navbarRoutes[userType];
    }

}

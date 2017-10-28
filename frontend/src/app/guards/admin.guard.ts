import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';

@Injectable()
export class AdminGuard implements CanActivate {

    constructor(private router: Router) { }

    canActivate() {

        let session = JSON.parse(localStorage.getItem('fb-session'));
        if (session.usertype === 'admin') {
            return true;
        }
        let redirectUrl = '/' + session.usertype;
        this.router.navigate([ redirectUrl ]);
        return false;
    }

}
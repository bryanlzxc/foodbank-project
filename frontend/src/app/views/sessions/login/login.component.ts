import { Component, OnInit, ViewChild } from '@angular/core';
import { MatProgressBar, MatButton } from '@angular/material';
import { AuthService } from './../../../services/auth.service';
import { Router } from '@angular/router';

@Component({
    selector: 'login',
    templateUrl: './login.component.html',
    styleUrls: [ './login.component.css' ]
})

export class LoginComponent {

    @ViewChild(MatProgressBar) progressBar: MatProgressBar;
    @ViewChild(MatButton) submitButton: MatButton;

    public signinData = {
        'username':'',
        'password':'',
    }

    constructor (private auth: AuthService, private router: Router) {

    }

    public signin () {
        this.auth.login(this.signinData.username, this.signinData.password)
            .subscribe(res => {
                if (res.status === 'success') {
                    let session_json = JSON.stringify({
                        'username': this.signinData.username,
                        'usertype': res.userType,
                        'token': res.token
                    });
                    localStorage.setItem('fb-session', session_json);
                    this.router.navigate(['/']);
                }
            });
    }

}

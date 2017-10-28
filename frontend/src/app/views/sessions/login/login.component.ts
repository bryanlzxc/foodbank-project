import { Component } from '@angular/core';

@Component({
    selector: 'login',
    templateUrl: './login.component.html',
    styleUrls: [ './login.component.css' ]
})

export class LoginComponent {

    public signinData = {
        'username':'',
        'password':'',
    }

    public signin () {
        console.log("SIGN IN");
    }

}

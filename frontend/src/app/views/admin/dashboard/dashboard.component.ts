import { Component, OnInit } from '@angular/core';
import { Http, Headers } from '@angular/http';

@Component({
    selector: 'adm-dash',
    templateUrl: './dashboard.component.html',
    styleUrls: [ './dashboard.component.css' ]
})

export class AdminDashboardComponent implements OnInit {

    constructor (private http: Http) {

    }

    ngOnInit () {
    }

    public test () {
        const header = new Headers({
            'Content-Type': 'application/json'
        });
        let temp = {
            username:'jaren',
            password:'password1'
        };
        this.http.post('https://foodbank-inventory-server.herokuapp.com/authenticate', temp, {
            headers: header
        }).subscribe(res => {
            console.log(res);
        });
    }

}

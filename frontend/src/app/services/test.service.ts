import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import services from './../../config/services';

@Injectable()
export class TestService {

    constructor (private http: Http) {

    }

    public testGet () {
        this.http.get(services['test-service']).subscribe(res => {
            console.log(res);
        });
    }

}

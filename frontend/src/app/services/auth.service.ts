import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
import services from './../../config/services';

interface LoginResponse {
    status: string;
    userType: string;
    token: string;
}

@Injectable()
export class AuthService {

    public authToken;

    constructor(private http: HttpClient) {

    }

    public login (username: string, password: string) {
        return this.http.get<LoginResponse>(services['auth-success']);
        //JSON.stringify({ username: username, password: password })
    }

}

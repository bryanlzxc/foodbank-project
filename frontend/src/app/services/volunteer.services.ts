import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import services from './../../config/services';

@Injectable()
export class VolunteerService {

    constructor (private http: HttpClient) {

    }

}

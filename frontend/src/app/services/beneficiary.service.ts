import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import services from './../../config/services';

@Injectable()
export class BeneficiaryService {

    constructor (private http: HttpClient) {
    	this.http.get(services.beneficiary.dashboard).subscribe(res => {
    		console.log("RES", res);
    		return res;
    	});
    }

    getBrowseInfo () {

    }

}

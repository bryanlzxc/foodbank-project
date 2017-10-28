import { Component, OnInit } from '@angular/core';
import * as _ from "lodash";
import { BeneficiaryService } from './../../../services/beneficiary.service';

@Component({
    selector: 'ben-dash',
    templateUrl: './dashboard.component.html',
    styleUrls: [ './dashboard.component.css' ]
})

export class BeneficiaryDashboardComponent implements OnInit {

    public pictures;
    public response;

    constructor (private benSvc: BeneficiaryService) {

    }

    ngOnInit () {
        this.benSvc.getBrowseInfo().subscribe(res => {
        	console.log(res);
            if (res) {
            	this.pictures = res['categoryList'];
            	this.response = _.cloneDeep(res);
            }
        });
    }

    browseButton(name){
    	let newCat = name + "List";
    	this.pictures = this.response[newCat];
    }
}

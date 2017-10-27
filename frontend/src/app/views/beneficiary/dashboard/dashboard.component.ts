import { Component, OnInit } from '@angular/core';
import { BeneficiaryService } from './../../../services/beneficiary.service';

@Component({
    selector: 'ben-dash',
    templateUrl: './dashboard.component.html',
    styleUrls: [ './dashboard.component.css' ]
})

export class BeneficiaryDashboardComponent implements OnInit {

	public pictures;

	constructor (private benSvc: BeneficiaryService) {

	}

	ngOnInit () {
		this.pictures = this.benSvc.getBrowseInfo();
		console.log('Browse');
		console.log(this.pictures);
	}
	
}

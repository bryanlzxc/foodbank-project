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
        this.benSvc.getBrowseInfo().subscribe(res => {
            if (res) this.pictures = res;
        });
    }

}

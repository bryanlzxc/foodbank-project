import { NgModule }                         from '@angular/core';
import { CommonModule }                     from '@angular/common';
import { FormsModule }                      from '@angular/forms';
import { RouterModule }                     from '@angular/router';
import { BeneficiaryRoutes }                from './beneficiary.routing';
import {
    MatCardModule,
    MatGridListModule
}                                           from '@angular/material';
import { BeneficiaryDashboardComponent }    from './dashboard/dashboard.component';
import { BeneficiaryInventoryComponent }    from './inventory/inventory.component';
import { BeneficiaryRequestComponent }      from './request/request.component';
import { BeneficiaryService }               from './../../services/beneficiary.service';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        MatCardModule,
        MatGridListModule,
        RouterModule.forChild(BeneficiaryRoutes)
    ],
    declarations: [
        BeneficiaryDashboardComponent,
        BeneficiaryInventoryComponent,
        BeneficiaryRequestComponent
    ],
    providers: [
        BeneficiaryService
    ],
    exports: [

    ]
})

export class BeneficiaryModule {}

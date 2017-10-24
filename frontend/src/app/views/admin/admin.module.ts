import { NgModule }                 from '@angular/core';
import { CommonModule }             from '@angular/common';
import { FormsModule }              from '@angular/forms';
import { RouterModule }             from '@angular/router';
import { AdminRoutes }              from './admin.routing';
import {
    MatCardModule,
    MatGridListModule
}                                   from '@angular/material';
import { AdminDashboardComponent }  from './dashboard/dashboard.component';
import { AdminInventoryComponent }  from './inventory/inventory.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        MatCardModule,
        MatGridListModule,
        RouterModule.forChild(AdminRoutes)
    ],
    declarations: [
        AdminDashboardComponent,
        AdminInventoryComponent
    ],
    providers: [

    ],
    exports: [

    ]
})

export class AdminModule {}

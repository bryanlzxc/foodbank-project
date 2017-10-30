import { NgModule }                         from '@angular/core';
import { CommonModule }                     from '@angular/common';
import { FormsModule, ReactiveFormsModule }                      from '@angular/forms';
import { RouterModule }                     from '@angular/router';
import { VolunteerRoutes }                  from './volunteer.routing';
import {
    MatCardModule,
    MatGridListModule,
    MatInputModule,
    MatDatepickerModule,
    MatSelectModule,
    MatProgressBarModule
}                                           from '@angular/material';
import { VolunteerDashboardComponent }      from './dashboard/dashboard.component';
import { VolunteerInventoryComponent }      from './inventory/inventory.component';
import { VolunteerPackingListComponent }    from './packing-list/packing-list.component';
import { SearchPipe }                       from './search.pipe';
import { VolunteerService }                 from './../../services/volunteer.services';
import { NgxDatatableModule }               from '@swimlane/ngx-datatable';

@NgModule({
    imports: [
        MatCardModule,
        MatGridListModule,
        MatInputModule,
        MatDatepickerModule,
        MatSelectModule,
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        NgxDatatableModule,
        MatProgressBarModule,
        RouterModule.forChild(VolunteerRoutes)
    ],
    declarations: [
        VolunteerDashboardComponent,
        VolunteerInventoryComponent,
        VolunteerPackingListComponent,
        SearchPipe,
    ],
    providers: [
        VolunteerService
    ],
    exports: [

    ]
})

export class VolunteerModule {}

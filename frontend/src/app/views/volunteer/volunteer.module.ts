import { NgModule }                         from '@angular/core';
import { CommonModule }                     from '@angular/common';
import { FormsModule }                      from '@angular/forms';
import { RouterModule }                     from '@angular/router';
import { VolunteerRoutes }                  from './volunteer.routing';
import {
    MatCardModule,
    MatGridListModule
}                                           from '@angular/material';
import { VolunteerDashboardComponent }      from './dashboard/dashboard.component';
import { VolunteerInventoryComponent }      from './inventory/inventory.component';
import { VolunteerPackingListComponent }    from './packing-list/packing-list.component';
import { SearchPipe }                       from './search.pipe';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        MatCardModule,
        MatGridListModule,
        RouterModule.forChild(VolunteerRoutes)
    ],
    declarations: [
        VolunteerDashboardComponent,
        VolunteerInventoryComponent,
        VolunteerPackingListComponent,
        SearchPipe
    ],
    providers: [

    ],
    exports: [

    ]
})

export class VolunteerModule {}

import { Routes } from '@angular/router';

import { VolunteerDashboardComponent } from './dashboard/dashboard.component';
import { VolunteerInventoryComponent } from './inventory/inventory.component';
import { VolunteerPackingListComponent } from './packing-list/packing-list.component';

export const VolunteerRoutes: Routes = [
    {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
    },
    {
        path: 'dashboard',
        component: VolunteerDashboardComponent
    },
    {
        path: 'inventory',
        component: VolunteerInventoryComponent
    },
    {
        path: 'packing-list',
        component: VolunteerPackingListComponent
    }

];

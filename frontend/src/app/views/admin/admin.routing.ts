import { Routes } from '@angular/router';
import { AdminDashboardComponent } from './dashboard/dashboard.component';
import { AdminInventoryComponent } from './inventory/inventory.component';
import { AdminAllocationComponent } from './allocation/allocation.component';
import { AdminPackingListComponent } from './packing-list/packing-list.component';

export const AdminRoutes: Routes = [
    {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
    },
    {
        path: 'dashboard',
        component: AdminDashboardComponent
    },
    {
        path: 'inventory',
        component: AdminInventoryComponent
    },
    {
        path: 'allocation',
        component: AdminAllocationComponent
    },
    {
        path: 'packing-list',
        component: AdminPackingListComponent
    }

];

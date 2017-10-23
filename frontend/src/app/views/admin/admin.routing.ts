import { Routes }               from '@angular/router';
import { InventoryComponent }   from './inventory/inventory.component';

export const AdminRoutes: Routes = [
    {
        path: 'inventory',
        component: InventoryComponent
    }
];

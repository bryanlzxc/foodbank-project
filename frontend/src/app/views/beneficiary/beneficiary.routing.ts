import { Routes }                           from '@angular/router';
import { BeneficiaryDashboardComponent }    from './dashboard/dashboard.component';
import { BeneficiaryInventoryComponent }    from './inventory/inventory.component';
import { BeneficiaryRequestComponent }      from './request/request.component';

export const BeneficiaryRoutes: Routes = [
    {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
    },
    {
        path: 'dashboard',
        component: BeneficiaryDashboardComponent
    },
    {
        path: 'inventory',
        component: BeneficiaryInventoryComponent
    },
    {
        path: 'request',
        component: BeneficiaryRequestComponent
    }

];

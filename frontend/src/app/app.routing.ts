import { Routes }                   from '@angular/router';
import { BaseLayoutComponent }      from './components/common/layouts/base-layout/base-layout.component';

export const rootRouterConfig: Routes = [
    {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
    },
    {
        path: '',
        component: BaseLayoutComponent,
        children: [
            {
                path: 'dashboard',
                loadChildren: './views/admin/admin.module#AdminModule'
            }
        ]
    }
];

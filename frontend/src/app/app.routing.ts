import { Routes }                   from '@angular/router';
import { BaseLayoutComponent }      from './components/common/layouts/base-layout/base-layout.component';

const userType = 'admin';

export const rootRouterConfig: Routes = [
    {
        path: '',
        redirectTo: userType,
        pathMatch: 'full'
    },
    {
        path: '',
        component: BaseLayoutComponent,
        children: [
            {
                path: 'sessions',
                loadChildren: './views/sessions/sessions.module#SessionsModule'
            }
        ]
    },
    {
        path: '',
        component: BaseLayoutComponent,
        children: [
            {
                path: 'admin',
                loadChildren: './views/admin/admin.module#AdminModule'
            },
            {
                path: 'volunteer',
                loadChildren: './views/volunteer/volunteer.module#VolunteerModule'
            },
            {
                path: 'beneficiary',
                loadChildren: './views/beneficiary/beneficiary.module#BeneficiaryModule'
            }
        ]
    },
    {
        path: '**',
        redirectTo: '/sessions/404'
    }
];

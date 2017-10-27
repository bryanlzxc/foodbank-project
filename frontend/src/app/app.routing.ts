import { Routes }                   from '@angular/router';
import { BaseLayoutComponent }      from './components/common/layouts/base-layout/base-layout.component';
import { AuthLayoutComponent }      from './components/common/layouts/auth-layout/auth-layout.component';
import { AuthService }              from './services/auth.service';

const userType = 'admin';

export const rootRouterConfig: Routes = [
    {
        path: '',
        redirectTo: userType,
        pathMatch: 'full'
    },
    {
        path: '',
        component: AuthLayoutComponent,
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
        canActivate: [ AuthService ],
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

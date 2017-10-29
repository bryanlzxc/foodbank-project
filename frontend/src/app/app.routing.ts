import { Routes }                   from '@angular/router';
import { BaseLayoutComponent }      from './components/common/layouts/base-layout/base-layout.component';
import { AuthLayoutComponent }      from './components/common/layouts/auth-layout/auth-layout.component';
import { AuthGuard }                from './guards/auth.guard';
import { AdminGuard }               from './guards/admin.guard';
import { BeneficiaryGuard }         from './guards/beneficiary.guard';
import { VolunteerGuard }           from './guards/volunteer.guard';

let session = JSON.parse(localStorage.getItem('fb-session'));
const userType = session ? session.usertype : 'sessions';

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
        canActivate: [ AuthGuard ],
        children: [
            {
                path: 'admin',
                canActivate: [ AdminGuard ],
                loadChildren: './views/admin/admin.module#AdminModule'
            },
            {
                path: 'volunteer',
                canActivate: [ VolunteerGuard ],
                loadChildren: './views/volunteer/volunteer.module#VolunteerModule'
            },
            {
                path: 'beneficiary',
                canActivate: [ BeneficiaryGuard ],
                loadChildren: './views/beneficiary/beneficiary.module#BeneficiaryModule'
            }
        ]
    },
    {
        path: '**',
        redirectTo: '/sessions/404'
    }
];

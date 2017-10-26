import { Routes }                           from '@angular/router';
import { PageNotFoundComponent }            from './page-not-found/page-not-found.component';

export const SessionsRoutes: Routes = [
    {
        path: '',
        children: [
            {
                path: '404',
                component: PageNotFoundComponent
            }

        ]
    },

];

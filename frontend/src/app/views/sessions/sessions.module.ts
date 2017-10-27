import { NgModule }                         from '@angular/core';
import { CommonModule }                     from '@angular/common';
import { FormsModule }                      from '@angular/forms';
import { RouterModule }                     from '@angular/router';
import { SessionsRoutes }                   from './sessions.routing';
import { PageNotFoundComponent }            from './page-not-found/page-not-found.component';
import { LoginComponent }                   from './login/login.component';
import {
    MatCardModule,
    MatProgressBarModule
}                                           from '@angular/material';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        MatCardModule,
        MatProgressBarModule,
        RouterModule.forChild(SessionsRoutes)
    ],
    declarations: [
        PageNotFoundComponent,
        LoginComponent
    ],
    providers: [

    ],
    exports: [

    ]
})

export class SessionsModule {}

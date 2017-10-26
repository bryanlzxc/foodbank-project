import { NgModule }                         from '@angular/core';
import { CommonModule }                     from '@angular/common';
import { FormsModule }                      from '@angular/forms';
import { RouterModule }                     from '@angular/router';
import { SessionsRoutes }                   from './sessions.routing';
import { PageNotFoundComponent }            from './page-not-found/page-not-found.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule.forChild(SessionsRoutes)
    ],
    declarations: [
        PageNotFoundComponent
    ],
    providers: [

    ],
    exports: [

    ]
})

export class SessionsModule {}

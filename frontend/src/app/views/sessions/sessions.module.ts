import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { SessionsRoutes } from './sessions.routing';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { LoginComponent } from './login/login.component';
import {
    MatCardModule,
    MatProgressBarModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    MatCheckboxModule,
    MatSnackBarModule
} from '@angular/material';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        MatCardModule,
        MatProgressBarModule,
        MatButtonModule,
        MatInputModule,
        MatFormFieldModule,
        MatCheckboxModule,
        MatSnackBarModule,
        RouterModule.forChild(SessionsRoutes)
    ],
    declarations: [
        PageNotFoundComponent,
        LoginComponent
    ]
})

export class SessionsModule {}

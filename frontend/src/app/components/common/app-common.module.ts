import { NgModule }             from '@angular/core';
import { CommonModule }         from '@angular/common';
import { FormsModule }          from '@angular/forms';
import { RouterModule }         from '@angular/router';
import {
    MatSidenavModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatListModule
}                               from '@angular/material';
import { BaseLayoutComponent }  from './layouts/base-layout/base-layout.component';
import { TopbarComponent }      from './topbar/topbar.component';
import { NavigationComponent }  from './navigation/navigation.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        MatSidenavModule,
        MatToolbarModule,
        MatButtonModule,
        MatIconModule,
        MatListModule
    ],
    declarations: [
        BaseLayoutComponent,
        TopbarComponent,
        NavigationComponent
    ],
    providers: [

    ],
    exports: [

    ]
})

export class AppCommonModule {}

import { NgModule }             from '@angular/core';
import { CommonModule }         from '@angular/common';
import { FormsModule }          from '@angular/forms';
import { RouterModule }         from '@angular/router';
import {
    MatSidenavModule,
    MatToolbarModule
}                               from '@angular/material';
import { BaseLayoutComponent }  from './layouts/base-layout/base-layout.component';
import { TopbarComponent }      from './layouts/topbar/topbar.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        MatSidenavModule,
        MatToolbarModule
    ],
    declarations: [
        BaseLayoutComponent,
        TopbarComponent
    ],
    providers: [

    ],
    exports: [

    ]
})

export class AppCommonModule {}

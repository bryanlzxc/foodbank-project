import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";

import { BaseLayoutComponent } from './base-layout.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule
    ],
    declarations: [
        BaseLayoutComponent
    ],
    providers: [

    ],
    exports: [

    ]
})

export class AppCommonModule {}

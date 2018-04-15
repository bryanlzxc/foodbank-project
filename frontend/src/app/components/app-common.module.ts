import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import {
    MatSidenavModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatListModule,
    MatMenuModule,
    MatProgressBarModule,
    MatDialogModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatTooltipModule
} from '@angular/material';
import { OnlyNumberDirective } from '@directives/only-number.directive';
import { PipeModule } from '@pipes/pipes.module';
import { ConfirmationModal } from './confirm-modal/confirm-modal.component';
import { ForgotPasswordModal } from './forgot-password-modal/forgot-password-modal.component';
import { SettingsModal } from '@components/settings-modal/settings-modal.component';
import { BaseLayoutComponent } from './layouts/base-layout/base-layout.component';
import { AuthLayoutComponent } from './layouts/auth-layout/auth-layout.component';
import { TopbarComponent } from './topbar/topbar.component';
import { NavigationComponent } from './navigation/navigation.component';
import { PaginationComponent } from './pagination/pagination.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        MatSidenavModule,
        MatToolbarModule,
        MatButtonModule,
        MatIconModule,
        MatListModule,
        MatMenuModule,
        MatProgressBarModule,
        MatDialogModule,
        MatProgressSpinnerModule,
        MatSnackBarModule,
        MatTooltipModule,
        PipeModule
    ],
    declarations: [
        BaseLayoutComponent,
        AuthLayoutComponent,
        TopbarComponent,
        NavigationComponent,
        OnlyNumberDirective,
        PaginationComponent,
        ConfirmationModal,
        ForgotPasswordModal,
        SettingsModal
    ],
    exports: [
        OnlyNumberDirective,
        PaginationComponent
    ],
    entryComponents: [
        ConfirmationModal,
        ForgotPasswordModal,
        SettingsModal
    ]
})

export class AppCommonModule {}

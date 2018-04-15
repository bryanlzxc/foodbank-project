import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { Http, HttpModule, Headers } from '@angular/http';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatSnackBarModule, MatProgressSpinnerModule } from '@angular/material';
import { AuthInterceptor } from './app.interceptor';
import { AppComponent } from './app.component';
import { ResetPasswordComponent } from '@views/sessions/reset-password/reset-password.component';
import { AppCommonModule } from './components/app-common.module';
import { RouterModule, PreloadAllModules } from '@angular/router';
import { rootRouterConfig } from './app.routing';
import {
    AuthService,
    UserService,
    HttpService,
    ProgressService,
    PaginationService,
    BreadcrumbService
} from './services/services';
import { AuthGuard } from './guards/auth.guard';
import { AdminGuard } from './guards/admin.guard';
import { VolunteerGuard } from './guards/volunteer.guard';
import { BeneficiaryGuard } from './guards/beneficiary.guard';

@NgModule({
    declarations: [
        AppComponent,
        ResetPasswordComponent
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        BrowserAnimationsModule,
        AppCommonModule,
        MatSnackBarModule,
        MatProgressSpinnerModule,
        HttpModule,
        RouterModule.forRoot(rootRouterConfig)
    ],
    providers: [
        AuthService,
        UserService,
        HttpService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true
        },
        ProgressService,
        PaginationService,
        BreadcrumbService,
        AuthGuard,
        AdminGuard,
        VolunteerGuard,
        BeneficiaryGuard
    ],
    bootstrap: [
        AppComponent
    ]
})
export class AppModule { }

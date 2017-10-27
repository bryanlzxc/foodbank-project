import { BrowserModule }            from '@angular/platform-browser';
import { BrowserAnimationsModule }  from '@angular/platform-browser/animations';
import { NgModule }                 from '@angular/core';
import { Http, HttpModule }         from '@angular/http';
import { HttpClientModule }         from '@angular/common/http';
import { AppComponent }             from './app.component';
import { AppCommonModule }          from './components/common/app-common.module';
import { RouterModule }             from '@angular/router';
import { rootRouterConfig }         from './app.routing';
import { TestService }              from './services/test.service';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    AppCommonModule,
    HttpModule,
    RouterModule.forRoot(rootRouterConfig, { useHash: false })
  ],
  providers: [
      TestService
  ],
  bootstrap: [
      AppComponent
  ]
})
export class AppModule { }

import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { Http, HttpModule } from '@angular/http';
import { AppComponent } from './app.component';
import { AppCommonModule } from './components/common/app-common.module';
import { RouterModule } from '@angular/router';
import { rootRouterConfig } from './app.routing';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    BrowserAnimationsModule,
    AppCommonModule,
    RouterModule.forRoot(rootRouterConfig, { useHash: false })
  ],
  providers: [

  ],
  bootstrap: [
      AppComponent
  ]
})
export class AppModule { }

import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from "@angular/router/testing";
import { NO_ERRORS_SCHEMA }          from '@angular/core';
import { FormsModule } from "@angular/forms";
import { Router } from "@angular/router";
import { Http } from '@angular/http';
import { BehaviorSubject } from 'rxjs';
import { TranslateModule, TranslateLoader, TranslateStaticLoader } from 'ng2-translate/ng2-translate';
import { 
  MdSidenavModule,
  MdListModule,
  MdTooltipModule,
  MdOptionModule,
  MdSelectModule,
  MdMenuModule,
  MdSnackBarModule,
  MdGridListModule,
  MdToolbarModule,
  MdIconModule,
  MdButtonModule,
  MdRadioModule,
  MdCheckboxModule,
  MdCardModule
} from '@angular/material';
import { AdminLayoutComponent } from './admin-layout.component';

export function createTranslateLoader(http: Http) {
  return new TranslateStaticLoader(http, './assets/i18n', '.json');
}

describe('AdminLayoutComponent', () => {
  let component: AdminLayoutComponent;
  let fixture: ComponentFixture<AdminLayoutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        FormsModule,
        MdSidenavModule,
        MdListModule,
        MdTooltipModule,
        MdOptionModule,
        MdSelectModule,
        MdMenuModule,
        MdSnackBarModule,
        MdGridListModule,
        MdToolbarModule,
        MdIconModule,
        MdButtonModule,
        MdRadioModule,
        MdCheckboxModule,
        MdCardModule,
        RouterTestingModule,
        TranslateModule.forRoot({
          provide: TranslateLoader,
          useFactory: (createTranslateLoader),
          deps: [Http]
        }),
      ],
      declarations: [ 
        AdminLayoutComponent
      ],
      providers: [],
      schemas: [ NO_ERRORS_SCHEMA ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
  
});

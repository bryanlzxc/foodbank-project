import { async, ComponentFixture, TestBed, inject } from '@angular/core/testing';
import { RouterTestingModule } from "@angular/router/testing";
import { Router, ActivatedRoute } from "@angular/router";
import { Http } from '@angular/http';
import { TranslateModule, TranslateLoader, TranslateStaticLoader } from 'ng2-translate/ng2-translate';
import { RoutePartsService } from '../../../services/route-parts/route-parts.service';
import { BreadcrumbComponent } from './breadcrumb.component';

export function createTranslateLoader(http: Http) {
  return new TranslateStaticLoader(http, './assets/i18n', '.json');
}

describe('BreadcrumbComponent', () => {
  let component: BreadcrumbComponent;
  let fixture: ComponentFixture<BreadcrumbComponent>;
  let routePartsServiceStub;

  beforeEach(async(() => {
    class RouterStub {
      events = {
        subscribe: (fn) => {fn()},
        filter: () => {
         return { subscribe: (fn) => {fn()}}
        }
      }
      createUrlTree() {}
      serializeUrl() {}
    }

    routePartsServiceStub = {
      generateRouteParts: (snapshot) => []
    };

    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        TranslateModule.forRoot({
          provide: TranslateLoader,
          useFactory: (createTranslateLoader),
          deps: [Http]
        })
      ],
      declarations: [BreadcrumbComponent],
      providers: [
        {provide: Router, useClass: RouterStub}, 
        {provide: ActivatedRoute, useValue: {snapshot: {}}},
        {provide: RoutePartsService, useValue: routePartsServiceStub}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BreadcrumbComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

});

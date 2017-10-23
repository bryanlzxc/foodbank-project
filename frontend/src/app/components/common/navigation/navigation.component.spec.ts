import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from "@angular/router/testing";
import { FormsModule } from "@angular/forms";
import { Http, HttpModule } from '@angular/http';
import { BehaviorSubject } from 'rxjs';
import { TranslateModule, TranslateLoader, TranslateStaticLoader } from 'ng2-translate/ng2-translate';
import { 
  MdIconModule,
  MdButtonModule,
  MdRadioModule,
  MdCardModule,
  MdTooltipModule
} from '@angular/material';
import { NavigationService } from "../../../services/navigation/navigation.service";
import { NavigationComponent } from './navigation.component';
export function createTranslateLoader(http: Http) {
  return new TranslateStaticLoader(http, './assets/i18n', '.json');
}

describe('NavigationComponent', () => {
  let component: NavigationComponent;
  let fixture: ComponentFixture<NavigationComponent>;

  beforeEach(async(() => {
    class navigationServiceStub {
      menuItems = new BehaviorSubject<any[]>([]);
      menuItems$ = this.menuItems.asObservable();
    }

    TestBed.configureTestingModule({
      imports: [
        FormsModule,
        MdCardModule,
        MdButtonModule,
        MdIconModule,
        MdRadioModule,
        MdTooltipModule,
        RouterTestingModule,
        TranslateModule.forRoot({
          provide: TranslateLoader,
          useFactory: (createTranslateLoader),
          deps: [Http]
        }),
      ],
      declarations: [ NavigationComponent ],
      providers: [{provide: NavigationService, useClass: navigationServiceStub}]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavigationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
  it('should create a menu item', () => {
    let noOfItems = component.menuItems.length;
    component.addMenuItem();
    let newNumber = component.menuItems.length;
    expect(noOfItems).toBeLessThan(newNumber);
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from "@angular/router/testing";
import { FormsModule } from "@angular/forms";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ThemeService } from '../../../services/theme/theme.service';
import * as domHelper from "../../../helpers/dom.helper";
import { 
  MdListModule,
  MdTooltipModule,
  MdOptionModule,
  MdSelectModule,
  MdMenuModule,
  MdGridListModule,
  MdToolbarModule,
  MdIconModule,
  MdButtonModule
} from '@angular/material';
import { TopbarComponent } from './topbar.component';

describe('TopbarComponent', () => {
  let component: TopbarComponent;
  let fixture: ComponentFixture<TopbarComponent>;
  let themeServiceStub;

  beforeEach(async(() => {
    class themeServiceStub {}
    
    TestBed.configureTestingModule({
      imports: [
        FormsModule,
        MdListModule,
        MdTooltipModule,
        MdOptionModule,
        MdSelectModule,
        MdMenuModule,
        MdGridListModule,
        MdToolbarModule,
        MdIconModule,
        MdButtonModule,
        BrowserAnimationsModule,
        RouterTestingModule
      ],
      declarations: [ TopbarComponent ],
      providers: [ {provide: ThemeService, useClass: themeServiceStub} ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TopbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  it('should toggle "collapsed-menu" class in body tag', () => {
    let before = domHelper.hasClass(document.body, 'collapsed-menu');
    component.toggleCollapse()
    let after = domHelper.hasClass(document.body, 'collapsed-menu');
    expect(before).toBe(!after);
  });
});

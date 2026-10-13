import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchEdocComponent } from './search-edoc.component';

describe('SearchEdocComponent', () => {
  let component: SearchEdocComponent;
  let fixture: ComponentFixture<SearchEdocComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchEdocComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchEdocComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

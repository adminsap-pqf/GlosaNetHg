import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DatastageTableComponent } from './datastage-table.component';

describe('DatastageTableComponent', () => {
  let component: DatastageTableComponent;
  let fixture: ComponentFixture<DatastageTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DatastageTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DatastageTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

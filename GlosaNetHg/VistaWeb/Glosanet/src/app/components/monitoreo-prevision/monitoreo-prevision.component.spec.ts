import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MonitoreoPrevisionComponent } from './monitoreo-prevision.component';

describe('MonitoreoPrevisionComponent', () => {
  let component: MonitoreoPrevisionComponent;
  let fixture: ComponentFixture<MonitoreoPrevisionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MonitoreoPrevisionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MonitoreoPrevisionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});

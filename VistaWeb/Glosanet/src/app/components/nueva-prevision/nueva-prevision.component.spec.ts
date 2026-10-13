import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NuevaPrevisionComponent } from './nueva-prevision.component';

describe('NuevaPrevisionComponent', () => {
  let component: NuevaPrevisionComponent;
  let fixture: ComponentFixture<NuevaPrevisionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NuevaPrevisionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NuevaPrevisionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});

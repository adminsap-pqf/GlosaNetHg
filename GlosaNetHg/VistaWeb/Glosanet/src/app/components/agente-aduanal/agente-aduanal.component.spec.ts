import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AgenteAduanalComponent } from './agente-aduanal.component';

describe('AgenteAduanalComponent', () => {
  let component: AgenteAduanalComponent;
  let fixture: ComponentFixture<AgenteAduanalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AgenteAduanalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AgenteAduanalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});

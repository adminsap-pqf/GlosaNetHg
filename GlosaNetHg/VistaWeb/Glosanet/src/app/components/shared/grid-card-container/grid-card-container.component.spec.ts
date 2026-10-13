import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GridCardContainerComponent } from './grid-card-container.component';

describe('GridCardContainerComponent', () => {
  let component: GridCardContainerComponent;
  let fixture: ComponentFixture<GridCardContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GridCardContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GridCardContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RgHomeComponent } from './rg-home.component';

describe('RgHomeComponent', () => {
  let component: RgHomeComponent;
  let fixture: ComponentFixture<RgHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RgHomeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RgHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

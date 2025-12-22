import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RgRegisterComponent } from './rg-register.component';

describe('RgRegisterComponent', () => {
  let component: RgRegisterComponent;
  let fixture: ComponentFixture<RgRegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RgRegisterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RgRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

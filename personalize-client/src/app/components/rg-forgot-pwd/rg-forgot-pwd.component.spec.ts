import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RgForgotPwdComponent } from './rg-forgot-pwd.component';

describe('RgForgotPwdComponent', () => {
  let component: RgForgotPwdComponent;
  let fixture: ComponentFixture<RgForgotPwdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RgForgotPwdComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RgForgotPwdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

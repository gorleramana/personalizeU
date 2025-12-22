import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RgLoginComponent } from './rg-login.component';

describe('RgLoginComponent', () => {
  let component: RgLoginComponent;
  let fixture: ComponentFixture<RgLoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RgLoginComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RgLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

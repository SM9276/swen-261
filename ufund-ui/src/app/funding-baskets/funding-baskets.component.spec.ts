import { ComponentFixture, TestBed } from '@angular/core/testing';

import { fundingBasketsComponent } from './funding-baskets.component';

describe('fundingBasketsComponent', () => {
  let component: fundingBasketsComponent;
  let fixture: ComponentFixture<fundingBasketsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [fundingBasketsComponent]
    });
    fixture = TestBed.createComponent(fundingBasketsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

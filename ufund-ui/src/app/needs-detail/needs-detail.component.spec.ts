import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NeedsDetailComponent } from './needs-detail.component';

describe('NeedsDetailComponent', () => {
  let component: NeedsDetailComponent;
  let fixture: ComponentFixture<NeedsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NeedsDetailComponent]
    });
    fixture = TestBed.createComponent(NeedsDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

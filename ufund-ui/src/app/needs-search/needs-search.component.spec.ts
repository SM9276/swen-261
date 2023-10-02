import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NeedsSearchComponent } from './needs-search.component';

describe('NeedsSearchComponent', () => {
  let component: NeedsSearchComponent;
  let fixture: ComponentFixture<NeedsSearchComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NeedsSearchComponent]
    });
    fixture = TestBed.createComponent(NeedsSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

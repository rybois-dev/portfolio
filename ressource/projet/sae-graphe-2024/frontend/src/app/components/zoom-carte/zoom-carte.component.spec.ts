import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZoomCarteComponent } from './zoom-carte.component';

describe('ZoomCarteComponent', () => {
  let component: ZoomCarteComponent;
  let fixture: ComponentFixture<ZoomCarteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ZoomCarteComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ZoomCarteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AcerdeComponent } from './acerde.component';

describe('AcerdeComponent', () => {
  let component: AcerdeComponent;
  let fixture: ComponentFixture<AcerdeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AcerdeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AcerdeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

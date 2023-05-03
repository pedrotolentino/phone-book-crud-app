import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactModificationComponent } from './contact-modification.component';

describe('ContactModificationComponent', () => {
  let component: ContactModificationComponent;
  let fixture: ComponentFixture<ContactModificationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContactModificationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContactModificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

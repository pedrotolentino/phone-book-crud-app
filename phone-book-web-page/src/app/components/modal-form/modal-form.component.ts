import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Contact } from '../../model/contact';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-modal-form',
  templateUrl: './modal-form.component.html',
  styleUrls: ['./modal-form.component.sass']
})
export class ModalFormComponent implements OnInit {

  @Input()
  modalTitle: string = "Contact Data";

  @Input()
  actionText: string = "Ok";

  @Input()
  contact: Contact = {firstName: '', lastName: '', phoneNumber: 0};

  constructor(private activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

  dismiss(reason: string):void {
    this.activeModal.dismiss(reason);
  }

  closeToAction(): void {
    this.activeModal.close(this.contact);
  }

  contactForm = new FormGroup({
    firstName: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
    phoneNumber: new FormControl('', Validators.compose([
      Validators.required,
      Validators.pattern(/^[0-9]*$/)
    ]))
  });
}

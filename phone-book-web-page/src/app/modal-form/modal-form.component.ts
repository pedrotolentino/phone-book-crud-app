import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Contact } from '../contact';

@Component({
  selector: 'app-modal-form',
  templateUrl: './modal-form.component.html',
  styleUrls: ['./modal-form.component.sass']
})
export class ModalFormComponent implements OnInit {

  @Input()
  contact!: Contact;

  constructor(private modalService: NgbModal,
              private activeModal: NgbActiveModal) { }

  ngOnInit(): void {
    console.log(this.contact);
  }

  dismiss():void {
    this.activeModal.dismiss();
  }

}

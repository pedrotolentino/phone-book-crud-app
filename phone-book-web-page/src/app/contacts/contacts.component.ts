import { Component, OnInit } from '@angular/core';
import { MOCK_CONTACTS } from '../mock-contacts';
import { Contact } from '../contact';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalFormComponent } from '../modal-form/modal-form.component';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.sass']
})
export class ContactsComponent implements OnInit {

  constructor(private modalService: NgbModal) { }

  ngOnInit(): void {
    this.refresh();
  }

  page = 1;
	pageSize = 4;
  contacts = MOCK_CONTACTS;
  collectionSize = MOCK_CONTACTS.length;

  refresh(): void {
		this.contacts = MOCK_CONTACTS.slice(
			(this.page - 1) * this.pageSize,
			(this.page - 1) * this.pageSize + this.pageSize,
		);
  }

  editContact(contact: Contact): void {
    const contactModel = this.modalService.open(ModalFormComponent, { ariaLabelledBy: 'modal-basic-title' })
    contactModel.componentInstance.contact = contact;
  }

  deleteContact(contact: Contact): void {
    console.log("Deleting the contact : "+contact.firstName);
  }

}

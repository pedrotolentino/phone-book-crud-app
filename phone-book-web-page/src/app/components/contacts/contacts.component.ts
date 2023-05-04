import { Component, OnInit } from '@angular/core';
import { Contact } from '../../model/contact';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalFormComponent } from '../modal-form/modal-form.component';
import { ContactService } from 'src/app/service/contact.service';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.sass']
})
export class ContactsComponent implements OnInit {

  constructor(private modalService: NgbModal,
              private contactService: ContactService) { }

  ngOnInit(): void {
    this.refresh();
  }

  page: number = 1;
	pageSize: number = 5;
  contacts: Contact[] = [];
  collectionSize: number = 0;

  refresh(): void {
    this.contactService.getContacts(this.page-1, this.pageSize)
      .subscribe({
        next: (response) => {
          this.contacts = response.contacts || [];
          this.page = response.pageNumber;
          this.pageSize = response.pageSize;
          this.collectionSize = response.totalElements;
        },
        error: (error) => {
          console.error(error);
          this.contacts = [];
        }
      });
  }

  addContact(): void {
    const contactModel = this.modalService.open(ModalFormComponent, { ariaLabelledBy: 'modal-basic-title' });
    contactModel.componentInstance.modalTitle = 'Add Contact';
    contactModel.componentInstance.actionText = 'Register';

    contactModel.result.then(
      (result) => {
        this.contactService.addContact(result)
          .subscribe({
            next: (response) => this.refresh(),
            error: (error) => console.error(error)
          });
      }
    );
  }

  editContact(contact: Contact): void {
    let modifiedContact = {...contact};
    const contactModel = this.modalService.open(ModalFormComponent, { ariaLabelledBy: 'modal-basic-title' });
    contactModel.componentInstance.contact = modifiedContact;
    contactModel.componentInstance.modalTitle = 'Edit Contact';
    contactModel.componentInstance.actionText = 'Update';

    contactModel.result.then(
      (updatedContact) => {
        this.contactService.updateContact(updatedContact)
          .subscribe({
            next: (response) => {
              let index = this.contacts.findIndex(contactEntry => contactEntry.id === response.id);
              this.contacts[index] = response;
            },
            error: (error) => console.error(error)
          });
      }
    );
  }

  deleteContact(contact: Contact): void {
    this.contactService.deleteContact(contact)
    .subscribe({
      next: () => this.refresh(),
      error: (error) => console.error(error)
    });
  }

}

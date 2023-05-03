import { Component, OnInit } from '@angular/core';
import { MOCK_CONTACTS } from '../mock-contacts';
import { Contact } from '../contact';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.sass']
})
export class ContactsComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  contacts = MOCK_CONTACTS;

  editContact(contact: Contact): void {
    console.log("Editing the contact : "+contact.firstName);
  }

  deleteContact(contact: Contact): void {
    console.log("Deleting the contact : "+contact.firstName);
  }

}

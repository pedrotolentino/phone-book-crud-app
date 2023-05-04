import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { ContactWrapper } from '../model/contact-wrapper';
import { Contact } from '../model/contact';

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  private contactsUrl = 'v1/contacts';

  constructor(private httpClient: HttpClient) { }

  getContacts(page: number, size: number): Observable<ContactWrapper> {
    return this.httpClient.get<ContactWrapper>(this.contactsUrl, {
      params: new HttpParams().set('page', page).set('size', size)
    });
  }

  addContact(newContact: Contact): Observable<Contact> {
    let body = 'firstName='+newContact.firstName+'&lastName='+newContact.lastName+'&phoneNumber='+newContact.phoneNumber ;
    
    return this.httpClient.post<Contact>(this.contactsUrl, body, {
      headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
    });
  }

  updateContact(contact: Contact): Observable<Contact> {
    return this.httpClient.put<Contact>(this.contactsUrl, contact);
  }

  deleteContact(contact: Contact): Observable<unknown> {
    return this.httpClient.delete<unknown>(this.contactsUrl, {
      body: contact
    });
  }
}

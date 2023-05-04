import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ContactWrapper } from './contact-wrapper';

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  private getContactsUrl = 'v1/contacts';

  constructor(private httpClient: HttpClient) { }

  getContacts(page: number, size: number): Observable<ContactWrapper> {
    return this.httpClient.get<ContactWrapper>(this.getContactsUrl, {
      params: new HttpParams().set('page', page).set('size', size)
    });
  }
}

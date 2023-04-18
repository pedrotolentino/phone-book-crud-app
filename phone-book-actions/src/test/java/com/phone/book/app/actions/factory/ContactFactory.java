package com.phone.book.app.actions.factory;

import com.phone.book.app.actions.model.Contact;

public class ContactFactory {

    public static Contact generateContact() {
        Contact contact = new Contact();

        contact.setId(1);
        contact.setFirstName("John");
        contact.setLastName("Wright");
        contact.setPhoneNumber(123);

        return contact;
    }
}

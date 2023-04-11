package com.phone.book.app.actions.service;

import com.phone.book.app.actions.exception.custom.ResourceNotFoundException;
import com.phone.book.app.actions.model.Contact;
import com.phone.book.app.actions.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = contactRepository.findAll();

        if (contacts.isEmpty()) throw new ResourceNotFoundException();

        return contacts;
    }

    public Contact registerContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public Contact updateContact(Contact contact) {
        if (contactRepository.existsById(contact.getId())) {
            return contactRepository.save(contact);
        } else {
            throw new ResourceNotFoundException(contact.getId());
        }
    }

    public void deleteContact(Contact contact) {
        if (contactRepository.existsById(contact.getId())) {
            contactRepository.delete(contact);
        } else {
            throw new ResourceNotFoundException(contact.getId());
        }
    }
}

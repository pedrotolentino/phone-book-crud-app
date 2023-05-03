package com.phone.book.app.actions.service;

import com.phone.book.app.actions.exception.custom.ResourceNotFoundException;
import com.phone.book.app.actions.factory.ContactFactory;
import com.phone.book.app.actions.model.Contact;
import com.phone.book.app.actions.model.wrapper.ContactWrapper;
import com.phone.book.app.actions.repository.ContactRepository;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @InjectMocks
    private ContactService contactService;

    @Mock
    private ContactRepository contactRepository;

    @Test
    public void shouldGetAllContacts() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Contact> contactsPage = new PageImpl<>(Collections.singletonList(ContactFactory.generateContact()));

        when(contactRepository.findAll(pageable)).thenReturn(contactsPage);

        ContactWrapper contactWrapper = contactService.getAllContacts(pageable);

        assertEquals(1, contactWrapper.getTotalElements());
        assertTrue(EqualsBuilder.reflectionEquals(ContactFactory.generateContact(), contactsPage.getContent().get(0)));
    }

    @Test
    public void shouldGetNoContacts() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Contact> contactsPage = new PageImpl<>(Collections.emptyList());

        when(contactRepository.findAll(pageable)).thenReturn(contactsPage);

        assertThrows(ResourceNotFoundException.class, () -> contactService.getAllContacts(pageable));
    }

    @Test
    public void shouldGetUnexpectedError() {
        Pageable pageable = PageRequest.of(0, 10);

        when(contactRepository.findAll(pageable)).thenThrow(new RuntimeException());

        assertThrows(Exception.class, () -> contactService.getAllContacts(pageable));
    }

    @Test
    public void shouldRegisterNewContact() {
        Contact contact = ContactFactory.generateContact();
        when(contactRepository.save(contact)).thenReturn(contact);

        Contact registeredContact = contactService.registerContact(contact);

        assertTrue(EqualsBuilder.reflectionEquals(ContactFactory.generateContact(), registeredContact));
    }

    @Test
    public void shouldUnexpectedErrorOnRegister() {
        Contact contact = ContactFactory.generateContact();

        when(contactRepository.save(contact)).thenThrow(new RuntimeException());

        assertThrows(Exception.class, () -> contactService.registerContact(contact));
    }

    @Test
    public void shouldUpdateContact() {
        Contact contact = ContactFactory.generateContact();
        contact.setLastName("Lennon");
        when(contactRepository.existsById(anyInt())).thenReturn(true);
        when(contactRepository.save(contact)).thenReturn(contact);

        Contact updatedContact = contactService.updateContact(contact);

        assertTrue(EqualsBuilder.reflectionEquals(contact, updatedContact));
    }

    @Test
    public void shouldNotFindContactToUpdate() {
        Contact contact = ContactFactory.generateContact();
        contact.setLastName("Lennon");
        when(contactRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> contactService.updateContact(contact));
    }

    @Test
    public void shouldUnexpectedErrorOnUpdate() {
        Contact contact = ContactFactory.generateContact();

        when(contactRepository.existsById(anyInt())).thenReturn(true);
        when(contactRepository.save(contact)).thenThrow(new RuntimeException());

        assertThrows(Exception.class, () -> contactService.updateContact(contact));
    }

    @Test
    public void shouldDeleteContact() {
        Contact contact = ContactFactory.generateContact();
        when(contactRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(contactRepository).delete(contact);

        contactService.deleteContact(contact);

        verify(contactRepository, times(1)).delete(contact);
    }

    @Test
    public void shouldNotFindContactToDelete() {
        Contact contact = ContactFactory.generateContact();
        when(contactRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> contactService.deleteContact(contact));
    }

    @Test
    public void shouldUnexpectedErrorOnDelete() {
        Contact contact = ContactFactory.generateContact();

        when(contactRepository.existsById(anyInt())).thenReturn(true);
        doThrow(new RuntimeException()).when(contactRepository).delete(contact);

        assertThrows(Exception.class, () -> contactService.deleteContact(contact));
    }
}

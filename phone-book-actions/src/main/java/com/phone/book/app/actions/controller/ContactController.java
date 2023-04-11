package com.phone.book.app.actions.controller;

import com.phone.book.app.actions.model.Contact;
import com.phone.book.app.actions.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController (ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Contact> getContacts() {
        return contactService.getAllContacts();
    }

    @PostMapping
    public ResponseEntity<Contact> registerContact(@ModelAttribute Contact contact) {
        Contact createdContact = contactService.registerContact(contact);
        return new ResponseEntity<>(contact, HttpStatus.CREATED);
    }

    @PutMapping
    public Contact updateContact(@RequestBody Contact contact) {
        return contactService.updateContact(contact);
    }

    @DeleteMapping
    public void deleteContact(@RequestBody Contact contact) {
        contactService.deleteContact(contact);
    }
}

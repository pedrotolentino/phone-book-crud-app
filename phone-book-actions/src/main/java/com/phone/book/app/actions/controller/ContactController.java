package com.phone.book.app.actions.controller;

import com.phone.book.app.actions.model.Contact;
import com.phone.book.app.actions.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<Contact> getContacts(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "5") int size,
                                     @RequestParam(defaultValue = "lastName,asc") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return contactService.getAllContacts(pageable);
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Contact> registerContact(@Valid Contact contact) {
        Contact createdContact = contactService.registerContact(contact);
        return new ResponseEntity<>(createdContact, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Contact> updateContact(@Valid @RequestBody Contact contact) {
        return ResponseEntity.ok(contactService.updateContact(contact));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteContact(@Valid @RequestBody Contact contact) {
        contactService.deleteContact(contact);
        return ResponseEntity.noContent().build();
    }
}

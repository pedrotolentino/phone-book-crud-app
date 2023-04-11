package com.phone.book.app.actions.controller;

import com.phone.book.app.actions.model.Contact;
import com.phone.book.app.actions.service.ContactService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

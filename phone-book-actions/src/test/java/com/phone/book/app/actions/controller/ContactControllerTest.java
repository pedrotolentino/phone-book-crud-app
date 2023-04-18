package com.phone.book.app.actions.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.phone.book.app.actions.exception.custom.ResourceNotFoundException;
import com.phone.book.app.actions.factory.ContactFactory;
import com.phone.book.app.actions.model.Contact;
import com.phone.book.app.actions.service.ContactService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService service;

    @Test
    public void shouldGetAllTheContacts() throws Exception {
        ObjectMapper mapper = new JsonMapper();
        Page<Contact> expectedContacts = new PageImpl<>(Collections.singletonList(ContactFactory.generateContact()));

        when(service.getAllContacts(any(Pageable.class))).thenReturn(expectedContacts);

        this.mockMvc.perform(get("/v1/contacts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedContacts)));
    }

    @Test
    public void shouldNotGetAnyContact() throws Exception {
        when(service.getAllContacts(any(Pageable.class))).thenThrow(new ResourceNotFoundException());

        this.mockMvc.perform(get("/v1/contacts"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldHandleAnUnexpectedErrorOnGetRequest() throws Exception {
        when(service.getAllContacts(any(Pageable.class))).thenThrow(new RuntimeException());

        this.mockMvc.perform(get("/v1/contacts"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void shouldCreateNewContact() throws Exception {
        ObjectMapper mapper = new JsonMapper();
        Contact expectedContact = ContactFactory.generateContact();

        when(service.registerContact(any(Contact.class))).thenReturn(expectedContact);

        this.mockMvc.perform(post("/v1/contacts")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", expectedContact.getFirstName())
                        .param("lastName", expectedContact.getLastName())
                        .param("phoneNumber", expectedContact.getPhoneNumber().toString()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(expectedContact)));
    }

    @Test
    public void shouldNotCreateNewContactByInvalidInput() throws Exception {
        this.mockMvc.perform(post("/v1/contacts")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", "")
                        .param("lastName", " ")
                        .param("phoneNumber", "null"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotCreateNewContactByUnexpectedError() throws Exception {
        Contact expectedContact = ContactFactory.generateContact();

        when(service.registerContact(any(Contact.class))).thenThrow(new RuntimeException());

        this.mockMvc.perform(post("/v1/contacts")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", expectedContact.getFirstName())
                        .param("lastName", expectedContact.getLastName())
                        .param("phoneNumber", expectedContact.getPhoneNumber().toString()))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void shouldUpdateContact() throws Exception {
        ObjectMapper mapper = new JsonMapper();
        Contact expectedContact = ContactFactory.generateContact();

        when(service.updateContact(any(Contact.class))).thenReturn(expectedContact);

        this.mockMvc.perform(put("/v1/contacts")
                        .content(mapper.writeValueAsString(expectedContact))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedContact)));
    }

    @Test
    public void shouldNotUpdateContactByInvalidInput() throws Exception {
        ObjectMapper mapper = new JsonMapper();

        this.mockMvc.perform(put("/v1/contacts")
                        .content(mapper.writeValueAsString(new Contact()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotUpdateContactByUnexpectedError() throws Exception {
        ObjectMapper mapper = new JsonMapper();
        Contact expectedContact = ContactFactory.generateContact();

        when(service.updateContact(any(Contact.class))).thenThrow(new RuntimeException());

        this.mockMvc.perform(put("/v1/contacts")
                        .content(mapper.writeValueAsString(expectedContact))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void shouldDeleteContact() throws Exception {
        ObjectMapper mapper = new JsonMapper();
        Contact expectedContact = ContactFactory.generateContact();

        doNothing().when(service).deleteContact(expectedContact);

        this.mockMvc.perform(delete("/v1/contacts")
                        .content(mapper.writeValueAsString(expectedContact))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldNotDeleteContactByInvalidInput() throws Exception {
        ObjectMapper mapper = new JsonMapper();

        this.mockMvc.perform(delete("/v1/contacts")
                        .content(mapper.writeValueAsString(new Contact()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotDeleteContactByUnexpectedError() throws Exception {
        ObjectMapper mapper = new JsonMapper();
        Contact expectedContact = ContactFactory.generateContact();

        doThrow(new RuntimeException()).when(service).deleteContact(any(Contact.class));

        this.mockMvc.perform(delete("/v1/contacts")
                        .content(mapper.writeValueAsString(expectedContact))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}

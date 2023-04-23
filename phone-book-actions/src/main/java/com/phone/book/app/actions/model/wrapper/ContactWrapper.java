package com.phone.book.app.actions.model.wrapper;

import com.phone.book.app.actions.model.Contact;
import org.springframework.data.domain.Page;

import java.util.List;

public class ContactWrapper {

    private List<Contact> contacts;

    private Integer pageNumber;

    private Integer pageSize;

    private Long totalElements;

    private Integer totalPages;

    public ContactWrapper(Page<Contact> pageContacts) {
        this.contacts = pageContacts.getContent();
        this.pageNumber = pageContacts.getNumber()+1;
        this.pageSize = pageContacts.getSize();
        this.totalElements = pageContacts.getTotalElements();
        this.totalPages = pageContacts.getTotalPages();
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}

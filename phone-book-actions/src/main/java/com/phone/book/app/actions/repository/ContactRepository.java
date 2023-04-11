package com.phone.book.app.actions.repository;

import com.phone.book.app.actions.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
}

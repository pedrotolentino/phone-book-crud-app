package com.phone.book.app.actions.exception.custom;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException() {
        super("There are no data for this resource");
    }

    public ResourceNotFoundException(Integer id) {
        super(String.format("There are no data for this resource: %d", id));
    }
}

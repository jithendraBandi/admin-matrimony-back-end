package projects.blue_whale.service;

import projects.blue_whale.entity.Contact;
import projects.exceptions.CustomException;

import java.util.List;

public interface ContactService {
    void saveContact(Contact contact) throws CustomException;

    List<Contact> getAllContacts();

    void deleteContact(Long contactIdId);
}

package projects.blue_whale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projects.blue_whale.dto.ApiResponse;
import projects.blue_whale.entity.Contact;
import projects.blue_whale.service.ContactService;
import projects.exceptions.CustomException;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api/contacts")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<ApiResponse> saveContact(@RequestBody Contact contact) throws CustomException {
        contactService.saveContact(contact);
        return new ResponseEntity<>(new ApiResponse("Contact updated successfully"), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllContacts() {
        List<Contact> contactList = contactService.getAllContacts();
        return new ResponseEntity<>(new ApiResponse(contactList), HttpStatus.OK);
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<ApiResponse> deleteContact(@PathVariable Long contactId) {
        contactService.deleteContact(contactId);
        return new ResponseEntity<>(new ApiResponse("Contact deleted successfully."), HttpStatus.OK);
    }
}

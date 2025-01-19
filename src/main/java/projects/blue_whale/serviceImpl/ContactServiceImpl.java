package projects.blue_whale.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import projects.blue_whale.constants.Constants;
import projects.blue_whale.entity.Contact;
import projects.blue_whale.repository.ContactRepository;
import projects.blue_whale.service.ContactService;
import projects.exceptions.CustomException;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepository contactRepository;

    @Override
    public void saveContact(Contact contact) throws CustomException {
        try {
            contactRepository.save(contact);
        }
        catch(DataIntegrityViolationException e) {
            throw new CustomException(Constants.DUPLICATE_ERROR);
        }
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public void deleteContact(Long contactId) {
        try {
            contactRepository.deleteById(contactId);
        }
        catch (DataIntegrityViolationException e) {
            throw new CustomException(Constants.ITEM_ASSOCIATION_ERROR);
        }
    }
}

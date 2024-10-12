package projects.blue_whale.serviceImpl;


import org.springframework.dao.DataIntegrityViolationException;
import projects.blue_whale.constants.Constants;
import projects.blue_whale.entity.Item;
import projects.blue_whale.repository.ItemRepository;
import projects.blue_whale.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projects.exceptions.CustomException;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void saveItem(Item item) throws CustomException {
        try {
            itemRepository.save(item);
        }
        catch(DataIntegrityViolationException e) {
            throw new CustomException(Constants.DUPLICATE_ERROR);
        }
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}

package projects.blue_whale.service;

import projects.blue_whale.entity.Item;
import projects.exceptions.CustomException;

import java.util.List;

public interface ItemService {
    void saveItem(Item item) throws CustomException;

    List<Item> getAllItems();
}

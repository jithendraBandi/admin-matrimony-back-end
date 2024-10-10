package projects.blue_whale.service;

import projects.blue_whale.entity.Item;

import java.util.List;

public interface ItemService {
    void saveItem(Item item);

    List<Item> getAllItems();
}

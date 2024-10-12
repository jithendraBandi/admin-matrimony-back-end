package projects.blue_whale.controller;

import projects.blue_whale.dto.ApiResponse;
import projects.blue_whale.entity.Item;
import projects.blue_whale.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projects.exceptions.CustomException;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveItem(@RequestBody Item item) throws CustomException {
        itemService.saveItem(item);
        return new ResponseEntity<>(new ApiResponse("Item Updated Successfully"), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse> getAllItems() {
        List<Item> itemList = itemService.getAllItems();
        return new ResponseEntity<>(new ApiResponse(itemList), HttpStatus.OK);
    }

    @DeleteMapping("/{itemId}/delete")
    public ResponseEntity<ApiResponse> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return new ResponseEntity<>(new ApiResponse("Item deleted successfully."), HttpStatus.OK);
    }
}

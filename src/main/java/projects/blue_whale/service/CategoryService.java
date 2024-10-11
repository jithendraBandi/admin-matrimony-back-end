package projects.blue_whale.service;

import projects.blue_whale.entity.Category;
import projects.exceptions.CustomException;

import java.util.List;

public interface CategoryService {
    void saveCategory(Category category) throws CustomException;

    List<Category> getAllCategories();
}

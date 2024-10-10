package projects.blue_whale.service;

import projects.blue_whale.entity.Category;

import java.util.List;

public interface CategoryService {
    void saveCategory(Category category);

    List<Category> getAllCategories();
}

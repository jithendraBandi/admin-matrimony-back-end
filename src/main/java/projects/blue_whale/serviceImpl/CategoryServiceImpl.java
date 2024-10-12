package projects.blue_whale.serviceImpl;

import org.springframework.dao.DataIntegrityViolationException;
import projects.blue_whale.constants.Constants;
import projects.blue_whale.entity.Category;
import projects.blue_whale.repository.CategoryRepository;
import projects.blue_whale.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projects.exceptions.CustomException;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void saveCategory(Category category) throws CustomException {
        try {
            categoryRepository.save(category);
        }
        catch(DataIntegrityViolationException e) {
            throw new CustomException(Constants.DUPLICATE_ERROR);
        }
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}

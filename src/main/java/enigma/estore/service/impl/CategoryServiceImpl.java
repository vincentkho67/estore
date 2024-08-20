package enigma.estore.service.impl;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import enigma.estore.model.Category;
import enigma.estore.repository.CategoryRepository;
import enigma.estore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> index() {
        return categoryRepository.findAll();
    }

    @Override
    public Category show(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));
    }

    @Override
    public Category create(Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category name cannot be empty");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Integer id, Category category) {
        Category exist = show(id);

        if (category.getName() != null && !category.getName().trim().isEmpty()) {
            exist.setName(category.getName().trim());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category name cannot be empty");
        }

        return categoryRepository.save(exist);
    }

    @Override
    public void delete(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
        categoryRepository.deleteById(id);
    }
}

package enigma.estore.service;

import enigma.estore.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> index();
    Category show(Integer id);
    Category create(Category category);
    Category update(Integer id, Category category);
    void delete(Integer id);
} 
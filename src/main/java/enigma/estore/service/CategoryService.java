package enigma.estore.service;

import enigma.estore.dto.request.category.CategoryDTO;
import enigma.estore.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    Page<CategoryDTO> index(CategoryDTO criteria, Pageable pageable);
    Category show(Integer id);
    Category create(Category category);
    Category update(Integer id, Category category);
    void delete(Integer id);
} 
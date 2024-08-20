package enigma.estore.service.impl;

import enigma.estore.dto.request.category.CategoryDTO;
import enigma.estore.utils.strings.ErrorResponseMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public Page<CategoryDTO> index(CategoryDTO criteria, Pageable pageable) {
        if (criteria != null && criteria.getName() != null && !criteria.getName().trim().isEmpty()) {
            return categoryRepository.findByNameContainingIgnoreCase(criteria.getName().trim(), pageable)
                    .map(CategoryDTO::from);
        }
        return categoryRepository.findAll(pageable).map(CategoryDTO::from);
    }

    @Override
    public Category show(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorResponseMessage.CATEGORY_NOT_FOUND));
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorResponseMessage.CATEGORY_NOT_FOUND);
        }
        categoryRepository.deleteById(id);
    }
}

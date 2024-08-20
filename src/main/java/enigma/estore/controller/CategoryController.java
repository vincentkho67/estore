package enigma.estore.controller;

import enigma.estore.model.Category;
import enigma.estore.service.CategoryService;
import enigma.estore.utils.strings.ApiUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrl.BASE_URL + ApiUrl.CATEGORIES)
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<Category> index() {
        return categoryService.index();
    }

    @GetMapping("/{id}")
    public Category show(@PathVariable int id) {
        return categoryService.show(id);
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        return categoryService.create(category);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable int id, @RequestBody Category category) {
        return categoryService.update(id, category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        categoryService.delete(id);
    }
}

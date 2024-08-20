package enigma.estore.controller;

import enigma.estore.dto.response.RenderJson;
import enigma.estore.model.Category;
import enigma.estore.service.CategoryService;
import enigma.estore.utils.strings.ApiUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> show(@PathVariable Integer id) {
        return RenderJson.basicFormat(categoryService.show(id), "Found", HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Category category) {
        return RenderJson.basicFormat(categoryService.create(category), "CREATED", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Category category) {
        return RenderJson.basicFormat(categoryService.update(id, category), "UPDATED", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        categoryService.delete(id);
    }
}

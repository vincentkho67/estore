package enigma.estore.repository;

import enigma.estore.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Integer> {
    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);
}

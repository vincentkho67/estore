package enigma.estore.repository;

import enigma.estore.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<Product, Integer>{
}

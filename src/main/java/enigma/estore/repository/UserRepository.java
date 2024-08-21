package enigma.estore.repository;

import enigma.estore.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Integer>{
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}

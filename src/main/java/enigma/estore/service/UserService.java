package enigma.estore.service;
import enigma.estore.dto.request.user.UserDTO;
import enigma.estore.dto.request.user.UserDTO.LoginRequest;
import enigma.estore.dto.request.user.UserDTO.RegisterRequest;
import enigma.estore.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User register(RegisterRequest req);
    String login(LoginRequest req);
    Page<UserDTO.UserBasicFormat> index(Pageable pageable);
}

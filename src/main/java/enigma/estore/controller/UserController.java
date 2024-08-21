package enigma.estore.controller;

import enigma.estore.dto.request.user.UserDTO;
import enigma.estore.dto.response.RenderJson;
import enigma.estore.service.UserService;
import enigma.estore.utils.strings.ApiUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiUrl.BASE_URL)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody UserDTO.RegisterRequest req) {
        return RenderJson.basicFormat(
                userService.register(req),
                "ok",
                HttpStatus.ACCEPTED
        );
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody UserDTO.LoginRequest req) {
        return RenderJson.basicFormat(
                userService.login(req),
                "Success",
                HttpStatus.OK
        );
    }

    @GetMapping("/users")
    public ResponseEntity<?> index(Pageable pageable) {
        return RenderJson.pageFormat(
                userService.index(pageable),
                "OK",
                HttpStatus.OK
        );
    }
}

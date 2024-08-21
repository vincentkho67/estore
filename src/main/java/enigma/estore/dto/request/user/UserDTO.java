package enigma.estore.dto.request.user;

import enigma.estore.model.User;
import enigma.estore.utils.enums.Role;
import lombok.Builder;
import lombok.Data;

public class UserDTO {
    @Data
    @Builder
    public static class UserBasicFormat {
        private String email;
        private String fullName;

        public static UserBasicFormat from(User user) {
            return UserBasicFormat.builder()
                    .fullName(user.getFirstName() + " " + user.getLastName())
                    .build();
        }
    }

    @Data
    @Builder
    public static class RegisterRequest {
        private String email;
        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private Role role;
    }

    @Data
    @Builder
    public static class LoginRequest {
        private String username;
        private String password;
    }

    @Data
    @Builder
    public static class UpdateRequest {
        private String firstName;
        private String lastName;
    }

    @Data
    @Builder
    public static class SearchRequest{
        private String firstName;
        private String lastName;
    }
}

package apartment.manager.presentation.models;


import apartment.manager.business.UserService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends BaseDto {
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = UserService.PASSWORD_REGEX, message = UserService.PASSWORD_VALIDATION_MESSAGE)
    private String password;

    @NotBlank(message = "Name is required")
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
package apartment.manager.presentation;

import apartment.manager.Utilities.JwtProvider;
import apartment.manager.Utilities.models.GlobalException;
import apartment.manager.Utilities.models.GlobalExceptionCode;
import apartment.manager.business.UserService;
import apartment.manager.entity.User;
import apartment.manager.presentation.models.LoginDto;
import apartment.manager.presentation.models.LoginResponse;
import apartment.manager.presentation.models.UpdatePasswordDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/authentication")
public class AuthController {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    @Lazy
    AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            User user = userService.loadUserByUsername(loginDto.getEmail());
            String jwt = jwtProvider.generateToken(user.getEmail(), user.getVersion());
            LoginResponse loginResponse = new LoginResponse(jwt);
            return ResponseEntity.ok().body(loginResponse);
        } catch (Exception e) {
            throw new GlobalException("Incorrect email or password.", GlobalExceptionCode.AUTHENTICATION, AuthenticationCredentialsNotFoundException.class);
        }
    }

    @PostMapping("/update-password")
    public ResponseEntity<Object> updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto) {
        userService.updateUsersPassword(updatePasswordDto.getEmail(), updatePasswordDto.getOldPassword(), updatePasswordDto.getNewPassword());
        return ResponseEntity.ok().body("Password was updated successfully");
    }
}

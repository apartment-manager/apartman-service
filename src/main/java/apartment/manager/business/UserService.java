package apartment.manager.business;

import apartment.manager.Utilities.mappers.UserMapper;
import apartment.manager.Utilities.models.GlobalException;
import apartment.manager.Utilities.models.GlobalExceptionCode;
import apartment.manager.entity.User;
import apartment.manager.presentation.models.UserDto;
import apartment.manager.repo.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService implements UserDetailsService {
    public static final String PASSWORD_VALIDATION_MESSAGE = "Password must be at least 8 characters and must have at least one lower case character, one uppercase character, one digit, and one special character from the following [@$!%*?&]";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new GlobalException("Couldn't find a user with email: {" + email + "}", GlobalExceptionCode.RESOURCE_NOT_FOUND, NoSuchElementException.class));
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return getUserByEmail(email);
        //return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    public void updateUsersPassword(String email, String oldPassword, String newPassword) {
        User user = getUserByEmail(email);
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
        } else {
            throw new GlobalException("The provided old password doesn't match the current password", GlobalExceptionCode.VALIDATION, ValidationException.class);
        }
        userRepository.save(user);
    }

    public void createUser(UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        try {
            userRepository.save(userMapper.userDtoToUser(userDto));
        } catch (DataIntegrityViolationException exception) {
            throw new GlobalException("There exist a user with the email: {" + userDto.getEmail() + "}", GlobalExceptionCode.UNIQUENESS, DataIntegrityViolationException.class);
        }
    }

    public void resetPassword(UserDto userDto) {
        User user = getUserByEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new GlobalException("Couldn't find a user with id: " + userId, GlobalExceptionCode.RESOURCE_NOT_FOUND, NoSuchElementException.class));
    }

}
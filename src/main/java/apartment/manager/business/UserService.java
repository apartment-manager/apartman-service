package apartment.manager.business;

import apartment.manager.Utilities.mappers.UserMapper;
import apartment.manager.Utilities.models.GlobalException;
import apartment.manager.Utilities.models.GlobalExceptionCode;
import apartment.manager.entity.User;
import apartment.manager.presentation.models.UserDto;
import apartment.manager.repo.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService implements UserDetailsService {
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

    public void createUser(UserDto userDto){
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        userRepository.save(userMapper.userDtoToUser(userDto));
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new GlobalException("Couldn't find a user with id: " + userId, GlobalExceptionCode.RESOURCE_NOT_FOUND, NoSuchElementException.class));
    }
}
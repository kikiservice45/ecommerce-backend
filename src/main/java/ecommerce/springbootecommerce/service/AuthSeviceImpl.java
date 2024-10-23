package ecommerce.springbootecommerce.service;

import ecommerce.springbootecommerce.dto.SignupRequest;
import ecommerce.springbootecommerce.dto.UserDto;
import ecommerce.springbootecommerce.entity.User;
import ecommerce.springbootecommerce.enums.UserRole;
import ecommerce.springbootecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthSeviceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

public UserDto createUser(SignupRequest signupRequest) {

User user = new User();
user.setEmail(signupRequest.getEmail());
user.setName(signupRequest.getName());
user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
user.setRole(UserRole.Customer);
User createdUser = userRepository.save(user);

UserDto userDto = new UserDto();
userDto.setId(createdUser.getId());
return userDto;
}

@Override
    public boolean hasUserWithEmail(String email)
    {
        return userRepository.findByEmail(email).isPresent();
    }

}

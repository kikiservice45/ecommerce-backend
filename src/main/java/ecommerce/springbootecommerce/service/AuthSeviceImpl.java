package ecommerce.springbootecommerce.service;

import ecommerce.springbootecommerce.dto.SignupRequest;
import ecommerce.springbootecommerce.dto.UserDto;
import ecommerce.springbootecommerce.entity.User;
import ecommerce.springbootecommerce.enums.UserRole;
import ecommerce.springbootecommerce.repository.UserRepository;
import jakarta.annotation.PostConstruct;
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

    @PostConstruct
    public void createAdminAccount(){
    User adminAccount = userRepository.findByRole(UserRole.Admin);
    if (null == adminAccount) {
        User user = new User();
        user.setEmail("mazensboui4@gmail.com");
        user.setName("Mezen");
        user.setRole(UserRole.Admin);
        user.setPassword(new BCryptPasswordEncoder().encode("97601525"));
        userRepository.save(user);


    }

    }
}

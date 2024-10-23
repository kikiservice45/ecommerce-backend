package ecommerce.springbootecommerce.service;

import ecommerce.springbootecommerce.dto.SignupRequest;
import ecommerce.springbootecommerce.dto.UserDto;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.ComponentScan;


public interface AuthService {
    UserDto createUser(SignupRequest signupRequest);

    boolean hasUserWithEmail(String email);
}

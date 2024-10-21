package ecommerce.springbootecommerce.service;

import ecommerce.springbootecommerce.dto.AuthenticationResponse;
import ecommerce.springbootecommerce.dto.RefreshTokenRequest;
import ecommerce.springbootecommerce.dto.RegisterRequest;
import ecommerce.springbootecommerce.entity.NotificationEmail;
import ecommerce.springbootecommerce.entity.User;
import ecommerce.springbootecommerce.entity.VerificationToken;
import ecommerce.springbootecommerce.exception.SpringEcommerceException;
import ecommerce.springbootecommerce.repository.UserRepository;
import ecommerce.springbootecommerce.repository.VerificationTokenRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final RefreshTokenService refreshTokenService;

    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setPassword(registerRequest.getPassword());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8090/api/auth/accountVerification/" + token));
    }



    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String email = verificationToken.getUser().getEmail();
        User user = userRepository.findByEmail("email").orElseThrow(() -> new SpringEcommerceException("User not found with name - " + email));
        user.setEnabled(true);
        userRepository.save(user);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringEcommerceException("Invalid Token")));
    }




}
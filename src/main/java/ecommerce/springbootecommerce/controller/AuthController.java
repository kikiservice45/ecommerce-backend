package ecommerce.springbootecommerce.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import ecommerce.springbootecommerce.dto.*;
import ecommerce.springbootecommerce.entity.User;
import ecommerce.springbootecommerce.repository.UserRepository;
import ecommerce.springbootecommerce.service.AuthService;
import ecommerce.springbootecommerce.service.RefreshTokenService;
import ecommerce.springbootecommerce.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import ecommerce.springbootecommerce.entity.RefreshToken;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

        private final AuthenticationManager authenticationManager;

        private final UserDetailsService userDetailsService;

        private final UserRepository userRepository;

        private final JwtUtil jwtUtil;


        public final static String HEADER_STRING="Authorization";
        public final static String TOKEN_PREFIX = "Bearer ";


    @PostMapping("/authenticate")
public void createAuthenticationToken( @RequestBody  AuthenticationRequest authenticationRequest,
                                       HttpServletResponse response) throws IOException, JSONException {

try {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
} catch (BadCredentialsException e) {
    throw new BadCredentialsException("Incorrect Username or password ");
}
final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> optionalUser = userRepository.findByEmail(userDetails.getUsername());
final String jwt = jwtUtil.generateToken(userDetails.getUsername());
if (optionalUser.isPresent()){

response.getWriter()
        .write(new JSONObject()
                .put("userId",optionalUser.get().getId())
                .put("role",optionalUser.get().getRole())
                .toString());


response.addHeader("HEADER_STRING", "TOKEN_PREFIX " + jwt);

}




    }






}
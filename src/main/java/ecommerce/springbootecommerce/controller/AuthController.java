package ecommerce.springbootecommerce.controller;

import ecommerce.springbootecommerce.dto.*;
import ecommerce.springbootecommerce.entity.User;
import ecommerce.springbootecommerce.repository.UserRepository;
import ecommerce.springbootecommerce.service.AuthService;
import ecommerce.springbootecommerce.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

        private final AuthenticationManager authenticationManager;

        private final UserDetailsService userDetailsService;

        private final UserRepository userRepository;

        private final JwtUtil jwtUtil;

        private final AuthService authService;

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
@PostMapping("/sign_up")
public ResponseEntity<?> SignupRequest(@RequestBody SignupRequest signupRequest) {
        if (authService.hasUserWithEmail(signupRequest.getEmail()))
        {
            return new ResponseEntity<>("User Alerady Exists", HttpStatus.NOT_ACCEPTABLE);

        }
UserDto userDto = authService.createUser(signupRequest);

return new ResponseEntity<>(userDto, HttpStatus.CREATED);



}



}
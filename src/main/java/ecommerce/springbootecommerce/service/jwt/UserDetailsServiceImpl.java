package ecommerce.springbootecommerce.service.jwt;

import ecommerce.springbootecommerce.entity.User;
import ecommerce.springbootecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

@Autowired
private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Optional<User> optioanUser =userRepository.findByEmail(username);

      if(optioanUser.isEmpty()) throw new UsernameNotFoundException(username);
      return new org.springframework.security.core.userdetails.User(optioanUser.get().getEmail(),optioanUser.get().getPassword(),new ArrayList<>());

    }
}

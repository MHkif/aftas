package yc.mhkif.aftas.services.UserDetailsServiceImpls;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import yc.mhkif.aftas.config.security.authenticators.AuthenticatedUser;
import yc.mhkif.aftas.repositories.UserRepository;

@Component
@AllArgsConstructor
@Slf4j
public class  EntityUserDetails implements UserDetailsService {
    private final UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) {
        var user = userRepo.findByEmail(email);

        if(user != null){
            AuthenticatedUser authUser = new AuthenticatedUser(user);

            log.info("User Name: "+user.getFirst_name());
            log.info("User Authorities : "+authUser.getAuthorities());
            return authUser;
        }else{
            throw new UsernameNotFoundException("User with email not found : "+email);
        }

    }
}
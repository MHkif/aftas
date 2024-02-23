package yc.mhkif.aftas.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yc.mhkif.aftas.entities.User;
import yc.mhkif.aftas.enums.IdentityDocumentType;
import yc.mhkif.aftas.enums.Role;
import yc.mhkif.aftas.repositories.UserRepository;

import java.lang.reflect.Member;
import java.time.LocalDateTime;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(
            UserRepository userRepository
    ) {

        return args -> {

           createManager(userRepository);

        };
    }

    private void createManager(UserRepository userRepository){
        User manager = new User();
        manager.setNum(207);
        manager.setFirst_name("AbdelMalek");
        manager.setLast_name("Achkif");
        manager.setEmail("malikhkif@gmail.com");
        manager.setPassword("aqwzsx");
        manager.setNationality("Moroccan");
        manager.setIdentityDocument(IdentityDocumentType.CIN);
        manager.setIdentityNumber("S700988");
        manager.setRole(Role.MANAGER);
        manager.setAccessionDate(LocalDateTime.now());

        log.info("Preloading Manager  : " +  userRepository.save(manager));
    }

}
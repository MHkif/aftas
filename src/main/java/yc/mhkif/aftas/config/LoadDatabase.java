package yc.mhkif.aftas.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import yc.mhkif.aftas.dto.requests.CompetitionRequest;
import yc.mhkif.aftas.dto.requests.FishRequest;
import yc.mhkif.aftas.dto.requests.LevelRequest;
import yc.mhkif.aftas.dto.requests.UserRequest;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.entities.Fish;
import yc.mhkif.aftas.entities.Level;
import yc.mhkif.aftas.entities.User;
import yc.mhkif.aftas.enums.IdentityDocumentType;
import yc.mhkif.aftas.enums.Role;
import yc.mhkif.aftas.exceptions.NotFoundException;
import yc.mhkif.aftas.repositories.*;
import yc.mhkif.aftas.services.impl.*;

import java.lang.reflect.Member;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(
             MemberServiceImpl memberService,
            CompetitionServiceImpl competitionService,
            LevelServiceImpl levelService,
            FishServiceImpl fishService,
            HuntingServiceImpl huntingService
    ) {

        return args -> {


           createManager(memberService);
           createMember(memberService);
            createJury(memberService);
            createCompetition(competitionService);
           createLevel(levelService);
           // createFish(fishService);


        };
    }

    private void createManager(MemberServiceImpl memberService){
        UserRequest manager = new UserRequest();
        manager.setFirst_name("AbdelMalek");
        manager.setLast_name("Achkif");
        manager.setEmail("malikhkif@gmail.com");
        manager.setPassword("aqwzsx");
        manager.setNationality("Moroccan");
        manager.setIdentityDocument(IdentityDocumentType.CIN);
        manager.setIdentityNumber("S700988");
        manager.setRole(Role.MANAGER);
        manager.setActivate(true);
        manager.setAccessionDate(LocalDateTime.now());

        log.info("Preloading Manager  : " +  memberService.create(manager));
    }


    private void createMember(MemberServiceImpl service){
        UserRequest member = new UserRequest();
        member.setFirst_name("Aziz");
        member.setLast_name("Harakati");
        member.setEmail("aziz@gmail.com");
        member.setPassword("aqwzsx");
        member.setNationality("Moroccan");
        member.setIdentityDocument(IdentityDocumentType.CIN);
        member.setIdentityNumber("HH68790");
        member.setRole(Role.MEMBER);
        member.setAccessionDate(LocalDateTime.now());

        log.info("Preloading Member  : " +  service.create(member));
    }

    private void createJury(MemberServiceImpl service){
        UserRequest member = new UserRequest();
        member.setFirst_name("Ahmed");
        member.setLast_name("Harakati");
        member.setEmail("ahmed@gmail.com");
        member.setPassword("aqwzsx");
        member.setNationality("Moroccan");
        member.setIdentityDocument(IdentityDocumentType.CIN);
        member.setIdentityNumber("HS87390");
        member.setRole(Role.JURY);
        member.setAccessionDate(LocalDateTime.now());

        log.info("Preloading Member  : " +  service.create(member));
    }

    private void createCompetition(CompetitionServiceImpl service){
        CompetitionRequest competition = new CompetitionRequest();
        competition.setDate(LocalDate.now());
        competition.setStartTime(LocalTime.parse("09:30"));
        competition.setEndTime(LocalTime.parse("17:00"));
        competition.setLocation("Safi");
        competition.setAmount(500.0);

        log.info("Preloading Competition  : " +  service.create(competition));
    }


    private void createLevel(LevelServiceImpl service){
        LevelRequest level = new LevelRequest();
        level.setDescription("Level A");
        level.setPoints(5);
        log.info("Preloading Level  : " +  service.create(level));
    }

    @Order(4)
    private void createFish(FishServiceImpl service){
        Level level = new Level();
        level.setCode(1);

        FishRequest fish = new FishRequest();
        fish.setName("Sardine");
        fish.setLevel(level);
        fish.setAverageWeight(80.0);

        log.info("Preloading Fish  : " +  service.create(fish));

    }

}
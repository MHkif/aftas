package yc.mhkif.aftas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.entities.Fish;
import yc.mhkif.aftas.entities.Hunting;
import yc.mhkif.aftas.entities.User;

import java.util.Optional;

public interface HuntingRepository extends JpaRepository<Hunting, Integer> {
    Optional<Hunting> findByFishAndUserAndCompetition(Fish fish, User user, Competition competition);
}

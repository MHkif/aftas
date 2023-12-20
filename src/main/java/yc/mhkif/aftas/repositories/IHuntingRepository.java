package yc.mhkif.aftas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.entities.Fish;
import yc.mhkif.aftas.entities.Hunting;
import yc.mhkif.aftas.entities.Member;

import java.util.Optional;

public interface IHuntingRepository extends JpaRepository<Hunting, Integer> {
    Optional<Hunting> findByFishAndMemberAndCompetition(Fish fish, Member member, Competition competition);
}

package yc.mhkif.aftas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.entities.Ranking;
import yc.mhkif.aftas.entities.implementations.CompetitionUser;

import java.util.List;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, CompetitionUser> {
    List<Ranking> findTop3ByCompetitionOrderByScoreDesc(Competition competition);

    List<Ranking> findAllByCompetition(Competition competition);
}

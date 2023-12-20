package yc.mhkif.aftas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yc.mhkif.aftas.dtos.responses.RankingResponse;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.entities.Member;
import yc.mhkif.aftas.entities.Ranking;
import yc.mhkif.aftas.entities.implementations.CompetitionMember;

import java.util.List;
import java.util.Optional;

public interface IRankingRepository extends JpaRepository<Ranking, CompetitionMember> {
    List<Ranking> findTop3ByCompetitionOrderByScoreDesc(Competition competition);

    List<Ranking> findAllByCompetition(Competition competition);
}

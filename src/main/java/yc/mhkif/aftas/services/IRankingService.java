package yc.mhkif.aftas.services;

import org.springframework.http.ResponseEntity;
import yc.mhkif.aftas.dtos.requests.RankingRequest;
import yc.mhkif.aftas.dtos.responses.RankingResponse;
import yc.mhkif.aftas.entities.Ranking;
import yc.mhkif.aftas.entities.implementations.CompetitionMember;

import java.util.List;

public interface IRankingService extends IService<Ranking, CompetitionMember, RankingRequest, RankingResponse>{
     ResponseEntity<List<Ranking>> getPodium(String competitionId);
    ResponseEntity<List<RankingResponse>> getByCompetition(String competitionId);


}

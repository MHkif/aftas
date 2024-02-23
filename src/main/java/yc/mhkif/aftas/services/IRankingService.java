package yc.mhkif.aftas.services;

import yc.mhkif.aftas.dto.requests.RankingRequest;
import yc.mhkif.aftas.dto.responses.RankingResponse;
import yc.mhkif.aftas.entities.Ranking;
import yc.mhkif.aftas.entities.implementations.CompetitionUser;

import java.util.List;

public interface IRankingService extends IService<Ranking, CompetitionUser, RankingRequest, RankingResponse>{
     List<Ranking> getPodium(String competitionId);
    List<RankingResponse> getByCompetition(String competitionId);


}

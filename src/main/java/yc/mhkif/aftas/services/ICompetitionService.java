package yc.mhkif.aftas.services;

import yc.mhkif.aftas.dto.requests.CompetitionRequest;
import yc.mhkif.aftas.dto.responses.CompetitionResponse;
import yc.mhkif.aftas.entities.Competition;

public interface ICompetitionService extends IService<Competition, String, CompetitionRequest, CompetitionResponse>{

   // ResponseEntity<List<Member>> getMembershipCompetition(String code);
}

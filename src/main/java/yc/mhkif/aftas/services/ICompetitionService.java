package yc.mhkif.aftas.services;

import org.springframework.http.ResponseEntity;
import yc.mhkif.aftas.dtos.requests.CompetitionRequest;
import yc.mhkif.aftas.dtos.responses.CompetitionResponse;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.entities.Member;

import java.util.List;

public interface ICompetitionService extends IService<Competition, String, CompetitionRequest, CompetitionResponse>{

   // ResponseEntity<List<Member>> getMembershipCompetition(String code);
}

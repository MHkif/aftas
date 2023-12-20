package yc.mhkif.aftas.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yc.mhkif.aftas.dtos.requests.CompetitionRequest;
import yc.mhkif.aftas.dtos.requests.RankingRequest;
import yc.mhkif.aftas.dtos.responses.CompetitionResponse;
import yc.mhkif.aftas.dtos.responses.RankingResponse;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.entities.Ranking;
import yc.mhkif.aftas.entities.implementations.CompetitionMember;
import yc.mhkif.aftas.services.IRankingService;

import java.util.List;

@RestController
@RequestMapping("aftas/api/v1/")
@CrossOrigin("*")
public class RankingController {

    private final IRankingService service;

    @Autowired
    public RankingController(IRankingService service) {
        this.service = service;
    }

    @GetMapping("rankings/search")
    public ResponseEntity<RankingResponse> getRanking(@RequestParam Integer member, @RequestParam String competition){
        CompetitionMember id = new CompetitionMember();
        id.setMemberId(member);
        id.setCompetitionCode(competition);
        return this.service.getById(id);
    }
    @GetMapping("rankings/competition/{code}")
    public ResponseEntity<List<RankingResponse>> getRankingsByCompetition(@PathVariable String code){
        return this.service.getByCompetition(code);
    }


    @GetMapping("rankings")
    public  ResponseEntity<Page<RankingResponse>> getRankings(@RequestParam int page, @RequestParam int size){
        return this.service.getAll(page, size);
    }

    @GetMapping("rankings/podium/{competitionId}")
    public  ResponseEntity<List<Ranking>> getPodium(@PathVariable String competitionId ){
        return this.service.getPodium(competitionId);
    }

    @PostMapping("rankings")
    public ResponseEntity<RankingResponse> createRanking(@Valid @RequestBody RankingRequest request){
        return service.create(request);
    }
}

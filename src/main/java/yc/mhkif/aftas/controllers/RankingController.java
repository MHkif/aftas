package yc.mhkif.aftas.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yc.mhkif.aftas.dto.HttpRes;
import yc.mhkif.aftas.dto.requests.RankingRequest;
import yc.mhkif.aftas.dto.responses.RankingResponse;
import yc.mhkif.aftas.entities.Ranking;
import yc.mhkif.aftas.entities.implementations.CompetitionUser;
import yc.mhkif.aftas.services.IRankingService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("aftas/api/v1/rankings")
public class RankingController {

    private final IRankingService service;

    @Autowired
    public RankingController(IRankingService service) {
        this.service = service;
    }

    @GetMapping("search")
    public ResponseEntity<HttpRes> getRanking(@RequestParam Integer member, @RequestParam String competition){
        CompetitionUser id = new CompetitionUser();
        id.setUserId(member);
        id.setCompetitionCode(competition);
        RankingResponse rankingResponse = this.service.getById(id);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .path("aftas/api/v1/rankings/search")
                        .status(HttpStatus.ACCEPTED)
                        .message("ranking has been retrieved successfully")
                        .developerMessage("ranking has been retrieved successfully")
                        .data(Map.of("response", rankingResponse))
                        .build()
        );
    }
    @GetMapping("competitions/{code}")
    public ResponseEntity<HttpRes> getRankingsByCompetition(@PathVariable String code){
        List<RankingResponse> rankingList  = this.service.getByCompetition(code);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .path("aftas/api/v1/rankings/competitions/")
                        .status(HttpStatus.ACCEPTED)
                        .message("ranking list has been retrieved successfully")
                        .developerMessage("ranking list has been retrieved successfully")
                        .data(Map.of("response", rankingList))
                        .build()
        );
    }


    @GetMapping("")
    public  ResponseEntity<HttpRes> getRankings(@RequestParam int page, @RequestParam int size){
        Page<RankingResponse> rankingPage  = this.service.getAll(page, size);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .path("aftas/api/v1/rankings")
                        .status(HttpStatus.ACCEPTED)
                        .message("ranking page has been retrieved successfully")
                        .developerMessage("ranking page has been retrieved successfully")
                        .data(Map.of("response", rankingPage))
                        .build()
        );
    }

    @GetMapping("podium/{competitionId}")
    public  ResponseEntity<HttpRes> getPodium(@PathVariable String competitionId ){
        List<Ranking> podium = this.service.getPodium(competitionId);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .path("aftas/api/v1/rankings")
                        .status(HttpStatus.ACCEPTED)
                        .message("podium has been retrieved successfully")
                        .developerMessage("podium has been retrieved successfully")
                        .data(Map.of("response", podium))
                        .build()
        );
    }

    @PostMapping("")
    public ResponseEntity<HttpRes> createRanking(@Valid @RequestBody RankingRequest request){
        RankingResponse response= service.create(request);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.CREATED.value())
                        .path("aftas/api/v1/rankings")
                        .status(HttpStatus.CREATED)
                        .message("ranking has been created successfully")
                        .developerMessage("ranking has been created successfully")
                        .data(Map.of("response", response))
                        .build()
        );
    }
}

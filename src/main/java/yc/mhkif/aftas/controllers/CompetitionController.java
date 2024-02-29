package yc.mhkif.aftas.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yc.mhkif.aftas.dto.HttpRes;
import yc.mhkif.aftas.dto.requests.CompetitionRequest;
import yc.mhkif.aftas.dto.responses.CompetitionResponse;
import yc.mhkif.aftas.services.ICompetitionService;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("aftas/api/v1/competitions")
public class CompetitionController {

    private final ICompetitionService service;

    @Autowired
    public CompetitionController(ICompetitionService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<HttpRes> getCompetitions(@RequestParam int page, @RequestParam int size){
        Page<CompetitionResponse> competitions = service.getAll(page,size);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .path("aftas/api/v1/competitions/")
                        .status(HttpStatus.ACCEPTED)
                        .message("competitions has been retrieved successfully")
                        .developerMessage("competitions has been retrieved successfully")
                        .data(Map.of("response", competitions))
                        .build()
        );
    }



    @GetMapping("{code}")
    public ResponseEntity<HttpRes> getCompetition(@PathVariable String code){
        CompetitionResponse competition = service.getById(code);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .path("aftas/api/v1/competitions/")
                        .status(HttpStatus.ACCEPTED)
                        .message("competition has been retrieved successfully")
                        .developerMessage("competition has been retrieved successfully")
                        .data(Map.of("response", competition))
                        .build()
        );
    }

    @PostMapping("")
    public ResponseEntity<HttpRes> createCompetition(@Valid @RequestBody CompetitionRequest request){
        CompetitionResponse competition= service.create(request);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.CREATED.value())
                        .path("aftas/api/v1/competitions/")
                        .status(HttpStatus.CREATED)
                        .message("competition has been created successfully")
                        .developerMessage("competition has been created successfully")
                        .data(Map.of("response", competition))
                        .build()
        );
    }
}

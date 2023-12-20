package yc.mhkif.aftas.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yc.mhkif.aftas.dtos.requests.CompetitionRequest;
import yc.mhkif.aftas.dtos.responses.CompetitionResponse;
import yc.mhkif.aftas.entities.Member;
import yc.mhkif.aftas.services.ICompetitionService;

import java.util.List;

@RestController
@RequestMapping("aftas/api/v1/")
@CrossOrigin("*")
public class CompetitionController {

    private final ICompetitionService service;

    @Autowired
    public CompetitionController(ICompetitionService service) {
        this.service = service;
    }

    @GetMapping("competitions")
    public ResponseEntity<Page<CompetitionResponse>> getCompetitions(@RequestParam int page, @RequestParam int size){
        return service.getAll(page,size);
    }



    @GetMapping("competitions/{code}")
    public ResponseEntity<CompetitionResponse> getCompetition(@PathVariable String code){
        return service.getById(code);
    }

    @PostMapping("competitions")
    public ResponseEntity<CompetitionResponse> createCompetition(@Valid @RequestBody CompetitionRequest request){
        return service.create(request);
    }
}

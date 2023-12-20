package yc.mhkif.aftas.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yc.mhkif.aftas.dtos.requests.LevelRequest;
import yc.mhkif.aftas.dtos.requests.MemberRequest;
import yc.mhkif.aftas.dtos.responses.LevelResponse;
import yc.mhkif.aftas.dtos.responses.MemberResponse;
import yc.mhkif.aftas.services.ILevelService;
import yc.mhkif.aftas.services.IMemberService;


@RestController
@RequestMapping("aftas/api/v1/")
@CrossOrigin("*")
public class LevelController {
    private final ILevelService service;

    @Autowired
    public LevelController(ILevelService service) {
        this.service = service;
    }


    @GetMapping("levels")
    public ResponseEntity<Page<LevelResponse>> getLevels(@RequestParam int page, @RequestParam int size){
        return service.getAll(page,size);
    }

    @GetMapping("levels/{code}")
    public ResponseEntity<LevelResponse> getLevel(@PathVariable int code){
        return service.getById(code);
    }


    @PostMapping("levels/save")
    public ResponseEntity<LevelResponse> createLevel(@Valid @RequestBody LevelRequest request){
        return service.create(request);
    }
}

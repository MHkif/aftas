package yc.mhkif.aftas.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yc.mhkif.aftas.dto.HttpRes;
import yc.mhkif.aftas.dto.requests.LevelRequest;
import yc.mhkif.aftas.dto.responses.LevelResponse;
import yc.mhkif.aftas.services.ILevelService;

import java.time.LocalDateTime;
import java.util.Map;


@RestController
@RequestMapping("aftas/api/v1/")
public class LevelController {
    private final ILevelService service;

    @Autowired
    public LevelController(ILevelService service) {
        this.service = service;
    }


    @GetMapping("levels")
    public ResponseEntity<HttpRes> getLevels(@RequestParam int page, @RequestParam int size){
        Page<LevelResponse> levels =  service.getAll(page,size);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .path("aftas/api/v1/levels/")
                        .status(HttpStatus.ACCEPTED)
                        .message("Levels has been retrieved successfully")
                        .developerMessage("Levels has been retrieved successfully")
                        .data(Map.of("response", levels))
                        .build()
        );
    }

    @GetMapping("levels/{code}")
    public ResponseEntity<HttpRes> getLevel(@PathVariable int code){

        LevelResponse level =  service.getById(code);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .path("aftas/api/v1/levels/")
                        .status(HttpStatus.ACCEPTED)
                        .message("Level has been retrieved successfully")
                        .developerMessage("Level has been retrieved successfully")
                        .data(Map.of("response", level))
                        .build()
        );

    }


    @PostMapping("levels")
    public ResponseEntity<HttpRes> createLevel(@Valid @RequestBody LevelRequest request){
        LevelResponse level = service.create(request);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.CREATED.value())
                        .path("aftas/api/v1/levels/")
                        .status(HttpStatus.CREATED)
                        .message("Level has been created successfully")
                        .developerMessage("Level has been created successfully")
                        .data(Map.of("response", level))
                        .build()
        );
    }
}

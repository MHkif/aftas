package yc.mhkif.aftas.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yc.mhkif.aftas.dto.FishesLevelResponse;
import yc.mhkif.aftas.dto.HttpRes;
import yc.mhkif.aftas.dto.requests.FishRequest;
import yc.mhkif.aftas.dto.responses.FishResponse;
import yc.mhkif.aftas.services.IFishService;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("aftas/api/v1/")
public class FishController {

    private final IFishService service;

    @Autowired
    public FishController(IFishService service) {
        this.service = service;
    }


    @GetMapping("fishes")
    public ResponseEntity<HttpRes> getFishes(@RequestParam int page, @RequestParam int size){
        Page<FishResponse> fishes =  service.getAll(page,size);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .path("aftas/api/v1/fishes/")
                        .status(HttpStatus.ACCEPTED)
                        .message("fishes has been retrieved successfully")
                        .developerMessage("fishes has been retrieved successfully")
                        .data(Map.of("response", fishes))
                        .build()
        );
    }

    @GetMapping("fishes/levels")
    public ResponseEntity<HttpRes> getFishesByLevel(@RequestParam int page, @RequestParam int size){
        Page<FishesLevelResponse> fishesLevel =  service.getFishesByLevel(page,size);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .path("aftas/api/v1/fishes/")
                        .status(HttpStatus.ACCEPTED)
                        .message("fishes by level has been retrieved successfully")
                        .developerMessage("fishes by level has been retrieved successfully")
                        .data(Map.of("response", fishesLevel))
                        .build()
        );
    }



    @GetMapping("fishes/{name}")
    public ResponseEntity<HttpRes> getFish(@PathVariable String name){
        FishResponse fish = service.getById(name);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .path("aftas/api/v1/fishes/")
                        .status(HttpStatus.ACCEPTED)
                        .message("fish has been retrieved successfully")
                        .developerMessage("fish has been retrieved successfully")
                        .data(Map.of("response", fish))
                        .build()
        );
    }




    @PostMapping("fishes")
    public ResponseEntity<HttpRes> createFish(@Valid @RequestBody FishRequest request){
        FishResponse fish = service.create(request);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.CREATED.value())
                        .path("aftas/api/v1/fishes/")
                        .status(HttpStatus.CREATED)
                        .message("fish has been created successfully")
                        .developerMessage("fish has been created successfully")
                        .data(Map.of("response", fish))
                        .build()
        );
    }
}

package yc.mhkif.aftas.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yc.mhkif.aftas.dto.HttpRes;
import yc.mhkif.aftas.dto.requests.HuntingRequest;
import yc.mhkif.aftas.dto.responses.HuntingResponse;
import yc.mhkif.aftas.services.IHuntingService;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("aftas/api/v1/huntings")
@RequiredArgsConstructor
public class HuntingController {

    private final IHuntingService service;

    @GetMapping("")
    public ResponseEntity<HttpRes> getHuntingList(@RequestParam int page, @RequestParam int size){
        Page<HuntingResponse> huntingList = service.getAll(page,size);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .path("aftas/api/v1/huntings/")
                        .status(HttpStatus.ACCEPTED)
                        .message("hunting List has been retrieved successfully")
                        .developerMessage("hunting List has been retrieved successfully")
                        .data(Map.of("response", huntingList))
                        .build()
        );
    }

    @GetMapping("{num}")
    public ResponseEntity<HttpRes> getHunting(@PathVariable int num){
        HuntingResponse hunting= service.getById(num);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .path("aftas/api/v1/huntings/")
                        .status(HttpStatus.ACCEPTED)
                        .message("hunting has been retrieved successfully")
                        .developerMessage("hunting has been retrieved successfully")
                        .data(Map.of("response", hunting))
                        .build()
        );
    }


    @PostMapping("")
    public ResponseEntity<HttpRes> createHunting(@Valid @RequestBody HuntingRequest request){
        HuntingResponse hunting=  service.create(request);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.CREATED.value())
                        .path("aftas/api/v1/huntings/")
                        .status(HttpStatus.CREATED)
                        .message("hunting has been created successfully")
                        .developerMessage("hunting has been created successfully")
                        .data(Map.of("response", hunting))
                        .build()
        );
    }
}

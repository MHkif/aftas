package yc.mhkif.aftas.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yc.mhkif.aftas.dtos.FishesLevelResponse;
import yc.mhkif.aftas.dtos.requests.FishRequest;
import yc.mhkif.aftas.dtos.responses.FishResponse;
import yc.mhkif.aftas.services.IFishService;

import java.util.List;

@RestController
@RequestMapping("aftas/api/v1/")
@CrossOrigin("*")
public class FishController {

    private final IFishService service;

    @Autowired
    public FishController(IFishService service) {
        this.service = service;
    }


    @GetMapping("fishes")
    public ResponseEntity<Page<FishResponse>> getFishes(@RequestParam int page, @RequestParam int size){
        return service.getAll(page,size);
    }

    @GetMapping("fishes/levels")
    public ResponseEntity<Page<FishesLevelResponse>> getFishesByLevel(@RequestParam int page, @RequestParam int size){
        return service.getFishesByLevel(page,size);
    }



    @GetMapping("fishes/{name}")
    public ResponseEntity<FishResponse> getFish(@PathVariable String name){
        return service.getById(name);
    }




    @PostMapping("fishes")
    public ResponseEntity<FishResponse> createFish(@Valid @RequestBody FishRequest request){
        return service.create(request);
    }
}

package yc.mhkif.aftas.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yc.mhkif.aftas.dtos.requests.HuntingRequest;
import yc.mhkif.aftas.dtos.requests.MemberRequest;
import yc.mhkif.aftas.dtos.responses.HuntingResponse;
import yc.mhkif.aftas.dtos.responses.MemberResponse;
import yc.mhkif.aftas.services.IHuntingService;

@RestController
@RequestMapping("aftas/api/v1/")
@CrossOrigin("*")
public class HuntingController {

    private final IHuntingService service;

    @Autowired
    public HuntingController(IHuntingService service) {
        this.service = service;
    }



    @GetMapping("huntings")
    public ResponseEntity<Page<HuntingResponse>> getHuntings(@RequestParam int page, @RequestParam int size){
        return service.getAll(page,size);
    }

    @GetMapping("huntings/{num}")
    public ResponseEntity<HuntingResponse> getHunting(@PathVariable int num){
        return service.getById(num);
    }


    @PostMapping("huntings")
    public ResponseEntity<HuntingResponse> createHunting(@Valid @RequestBody HuntingRequest request){
        return service.create(request);
    }
}

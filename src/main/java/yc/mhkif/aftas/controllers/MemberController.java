package yc.mhkif.aftas.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yc.mhkif.aftas.dtos.requests.MemberRequest;
import yc.mhkif.aftas.dtos.responses.MemberResponse;
import yc.mhkif.aftas.services.IMemberService;
import yc.mhkif.aftas.services.impl.MemberServiceImpl;

@RestController
@RequestMapping("aftas/api/v1/")
@CrossOrigin("*")
public class MemberController {

    private final IMemberService memberService;

    @Autowired
    public MemberController(IMemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("members")
    public ResponseEntity<Page<MemberResponse>> getMembers(@RequestParam int page, @RequestParam int size){
        return memberService.getAll(page,size);
    }

    @GetMapping("members/{num}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable int num){
        return memberService.getById(num);
    }


    @PostMapping("members")
    public ResponseEntity<MemberResponse> createMember(@Valid @RequestBody MemberRequest request){
        return memberService.create(request);
    }

    @GetMapping("members/checkExistence")
    public ResponseEntity<MemberResponse> checkMember(@Valid @RequestParam String identity){
        return memberService.checkExistence(identity);
    }
}

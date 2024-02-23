package yc.mhkif.aftas.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yc.mhkif.aftas.config.security.authenticators.AuthenticatedUser;
import yc.mhkif.aftas.config.security.jwt.JwtService;
import yc.mhkif.aftas.dto.HttpRes;
import yc.mhkif.aftas.dto.requests.AuthReq;
import yc.mhkif.aftas.dto.requests.MemberRequest;
import yc.mhkif.aftas.dto.responses.MemberResponse;
import yc.mhkif.aftas.entities.User;
import yc.mhkif.aftas.services.IMemberService;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("aftas/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final IMemberService memberService;
    private final JwtService jwtService;
    private final ModelMapper mapper;


    @GetMapping("")
    public ResponseEntity<HttpRes> getMembers(@RequestParam int page, @RequestParam int size){

        Page<MemberResponse> members =  memberService.getAll(page,size);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .path("aftas/api/v1/members/auth")
                        .status(HttpStatus.ACCEPTED)
                        .message("User has been authenticated")
                        .developerMessage("User has been authenticated")
                        .data(Map.of("response", members))
                        .build()
        );
    }

    @GetMapping("{num}")
    public ResponseEntity<HttpRes> getMember(@PathVariable int num){
        MemberResponse member =  memberService.getById(num);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .path("aftas/api/v1/members/")
                        .status(HttpStatus.ACCEPTED)
                        .message("User has been retrieved successfully")
                        .developerMessage("User has been retrieved successfully")
                        .data(Map.of("response", member))
                        .build()
        );
    }


    @PostMapping("")
    public ResponseEntity<HttpRes> createMember(@Valid @RequestBody MemberRequest request){
        MemberResponse member = memberService.create(request);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.CREATED.value())
                        .path("aftas/api/v1/members/")
                        .status(HttpStatus.CREATED)
                        .message("User has been created successfully")
                        .developerMessage("User has been created successfully")
                        .data(Map.of("response", member))
                        .build()
        );
    }
    @PostMapping("/auth")
    public ResponseEntity<HttpRes> auth(@Valid @RequestBody AuthReq auth){
        User user = memberService.auth(auth.getEmail(), auth.getPassword());
        AuthenticatedUser authenticatedEntity = new AuthenticatedUser(user);

        var token = jwtService.generateToken(authenticatedEntity);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .path("myrh/api/v1/members")
                        .status(HttpStatus.ACCEPTED)
                        .message(user.getRole()+" has been authenticated")
                        .developerMessage(user.getRole()+" has been authenticated")
                        .data(Map.of("response", mapper.map(user, MemberResponse.class), "token", token))
                        .build()
        );
    }

    @GetMapping("members/checkExistence")
    public ResponseEntity<HttpRes> checkMember(@Valid @RequestParam String identity){
        MemberResponse member = memberService.checkExistence(identity);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.CREATED.value())
                        .path("aftas/api/v1/members/checkExistence")
                        .status(HttpStatus.CREATED)
                        .message(member.getRole() + " User has been found successfully")
                        .developerMessage(member.getRole() + " User has been found successfully")
                        .data(Map.of("response", member))
                        .build()
        );
    }
}

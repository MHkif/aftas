package yc.mhkif.aftas.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import yc.mhkif.aftas.config.security.authenticators.AuthenticatedUser;
import yc.mhkif.aftas.config.security.jwt.JwtService;
import yc.mhkif.aftas.dto.HttpRes;
import yc.mhkif.aftas.dto.requests.AuthReq;
import yc.mhkif.aftas.dto.requests.UserRequest;
import yc.mhkif.aftas.dto.responses.UserResponse;
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

        Page<UserResponse> members =  memberService.getAll(page,size);
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
        UserResponse member =  memberService.getById(num);
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



    @GetMapping("checkExistence")
    public ResponseEntity<HttpRes> checkMember(@Valid @RequestParam String identity){
        UserResponse member = memberService.checkExistenceByIdentity(identity);
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

    @GetMapping("account-activation/{identity}")
    public ResponseEntity<HttpRes> activateAccount(@PathVariable String identity) {
        boolean isActivated = memberService.activateAccount(identity);

        HttpRes response = HttpRes.builder()
                .timeStamp(LocalDateTime.now().toString())
                .statusCode(HttpStatus.ACCEPTED.value())
                .path("aftas/api/v1/members/")
                .status(HttpStatus.ACCEPTED)
                .message("User account has been activated successfully")
                .developerMessage("User account has been activated successfully")
                .data(Map.of("response", isActivated))
                .build();

        return ResponseEntity.accepted().body(response);
    }
}

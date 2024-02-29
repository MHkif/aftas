package yc.mhkif.aftas.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("aftas/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IMemberService memberService;
    private final JwtService jwtService;
    private final ModelMapper mapper;


    @PostMapping("register")
    public ResponseEntity<HttpRes> createMember(@Valid @RequestBody UserRequest request){
        UserResponse member = memberService.create(request);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.CREATED.value())
                        .path("aftas/api/v1/auth/register")
                        .status(HttpStatus.CREATED)
                        .message("User has been created successfully")
                        .developerMessage("User has been created successfully")
                        .data(Map.of("response", member))
                        .build()
        );
    }
    @PostMapping("login")
    public ResponseEntity<HttpRes> login(@Valid @RequestBody AuthReq auth){
        User user = memberService.auth(auth.getEmail(), auth.getPassword());
        AuthenticatedUser authenticatedEntity = new AuthenticatedUser(user);

        var token = jwtService.generateToken(authenticatedEntity);
        return ResponseEntity.accepted().body(
                HttpRes.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .path("myrh/api/v1/auth")
                        .status(HttpStatus.ACCEPTED)
                        .message(user.getRole()+" has been authenticated")
                        .developerMessage(user.getRole()+" has been authenticated")
                        .data(Map.of("response", mapper.map(user, UserResponse.class), "token", token))
                        .build()
        );
    }


}

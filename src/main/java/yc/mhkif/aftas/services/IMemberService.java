package yc.mhkif.aftas.services;

import org.springframework.http.ResponseEntity;
import yc.mhkif.aftas.dtos.requests.MemberRequest;
import yc.mhkif.aftas.dtos.responses.MemberResponse;
import yc.mhkif.aftas.entities.Member;

import java.util.List;
import java.util.Optional;

public interface IMemberService extends IService<Member, Integer, MemberRequest, MemberResponse>{

    ResponseEntity<MemberResponse> checkExistence(String identity);
}

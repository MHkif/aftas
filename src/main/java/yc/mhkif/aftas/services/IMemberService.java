package yc.mhkif.aftas.services;

import yc.mhkif.aftas.dto.requests.MemberRequest;
import yc.mhkif.aftas.dto.responses.MemberResponse;
import yc.mhkif.aftas.entities.User;

public interface IMemberService extends IService<User, Integer, MemberRequest, MemberResponse>{
    MemberResponse checkExistence(String identity);

    User auth(String email, String password);
}

package yc.mhkif.aftas.services;

import yc.mhkif.aftas.dto.requests.UserRequest;
import yc.mhkif.aftas.dto.responses.UserResponse;
import yc.mhkif.aftas.entities.User;

public interface IMemberService extends IService<User, Integer, UserRequest, UserResponse>{
    UserResponse checkExistenceByIdentity(String identity);
    UserResponse checkExistenceByEmail(String identity);
    User auth(String email, String password);

    Boolean activateAccount(String identity);
}

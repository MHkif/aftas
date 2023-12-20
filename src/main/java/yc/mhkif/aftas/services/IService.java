package yc.mhkif.aftas.services;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import yc.mhkif.aftas.dtos.requests.MemberRequest;
import yc.mhkif.aftas.dtos.responses.MemberResponse;
import yc.mhkif.aftas.entities.Member;

import java.util.List;

public interface IService<Entity, pk, Req, Res> {
    ResponseEntity<Res> getById(pk id);

    ResponseEntity<Page<Res>> getAll(int page, int size);

    ResponseEntity<Res> create(Req request);

    ResponseEntity<Res> update(int id, Req request);

    ResponseEntity<Void> deleteById(int id);
    Entity resToEntity(Res res);
    Entity reqToEntity(Req req);
    Req toRequest(Entity e);
    Res toResponse(Entity e);


}

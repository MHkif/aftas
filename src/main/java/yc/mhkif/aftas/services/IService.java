package yc.mhkif.aftas.services;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface IService<Entity, pk, Req, Res>  {
    Res getById(pk id);
    Page<Res> getAll(int page, int size);
    Res create(Req request);
    Res update(pk id, Entity entity);


}

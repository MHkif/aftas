package yc.mhkif.aftas.services;


import yc.mhkif.aftas.dto.requests.HuntingRequest;
import yc.mhkif.aftas.dto.responses.HuntingResponse;
import yc.mhkif.aftas.entities.Hunting;

public interface IHuntingService extends IService<Hunting, Integer, HuntingRequest, HuntingResponse> {
}

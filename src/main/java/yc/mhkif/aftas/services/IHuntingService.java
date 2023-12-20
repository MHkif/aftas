package yc.mhkif.aftas.services;


import yc.mhkif.aftas.dtos.requests.HuntingRequest;
import yc.mhkif.aftas.dtos.responses.HuntingResponse;
import yc.mhkif.aftas.entities.Hunting;

public interface IHuntingService extends IService<Hunting, Integer, HuntingRequest, HuntingResponse> {
}

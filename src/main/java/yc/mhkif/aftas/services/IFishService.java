package yc.mhkif.aftas.services;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import yc.mhkif.aftas.dto.FishesLevelResponse;
import yc.mhkif.aftas.dto.requests.FishRequest;
import yc.mhkif.aftas.dto.responses.FishResponse;
import yc.mhkif.aftas.entities.Fish;

public interface IFishService extends IService<Fish, String, FishRequest, FishResponse>{
     Page<FishesLevelResponse> getFishesByLevel(int page, int size) ;

    }

package yc.mhkif.aftas.services;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import yc.mhkif.aftas.dtos.FishesLevelResponse;
import yc.mhkif.aftas.dtos.requests.FishRequest;
import yc.mhkif.aftas.dtos.responses.FishResponse;
import yc.mhkif.aftas.entities.Fish;

import java.util.List;

public interface IFishService extends IService<Fish, String, FishRequest, FishResponse>{
     ResponseEntity<Page<FishesLevelResponse>> getFishesByLevel(int page, int size) ;

    }

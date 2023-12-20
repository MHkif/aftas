package yc.mhkif.aftas.dtos;

import lombok.Builder;
import lombok.Data;
import yc.mhkif.aftas.dtos.responses.FishResponse;
import yc.mhkif.aftas.dtos.responses.LevelResponse;
import yc.mhkif.aftas.entities.Level;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
public class FishesLevelResponse {
    private Level level;
    private Collection<FishResponse> fishResponses ;


}

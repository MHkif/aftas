package yc.mhkif.aftas.dto;

import lombok.Builder;
import lombok.Data;
import yc.mhkif.aftas.dto.responses.FishResponse;
import yc.mhkif.aftas.entities.Level;

import java.util.Collection;

@Data
@Builder
public class FishesLevelResponse {
    private Level level;
    private Collection<FishResponse> fishResponses ;


}

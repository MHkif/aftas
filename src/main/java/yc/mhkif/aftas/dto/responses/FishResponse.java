package yc.mhkif.aftas.dto.responses;

import lombok.Data;
import yc.mhkif.aftas.entities.Level;

@Data
public class FishResponse {
    private String name;
    private Double averageWeight;
    private Level level;

}

package yc.mhkif.aftas.dtos.responses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import yc.mhkif.aftas.entities.Level;

@Data
public class FishResponse {
    private String name;
    private Double averageWeight;
    private Level level;

}

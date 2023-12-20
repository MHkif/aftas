package yc.mhkif.aftas.dtos.responses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import yc.mhkif.aftas.entities.Fish;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class LevelResponse {
    private Integer code;
    private String description;
    private Integer points;

}

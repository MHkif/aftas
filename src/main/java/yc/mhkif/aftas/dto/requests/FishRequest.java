package yc.mhkif.aftas.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import yc.mhkif.aftas.entities.Level;

@Data
public class FishRequest {
    @NotBlank(message = "Name is Required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @NotNull(message = "Average Weight is Required")
    private Double averageWeight;

    @NotNull(message = "Level is Required")
    private Level level;

}

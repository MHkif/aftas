package yc.mhkif.aftas.dtos.requests;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import yc.mhkif.aftas.entities.Hunting;
import yc.mhkif.aftas.entities.Level;

import java.util.ArrayList;
import java.util.Collection;

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

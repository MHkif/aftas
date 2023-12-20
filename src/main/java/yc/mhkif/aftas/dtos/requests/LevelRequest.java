package yc.mhkif.aftas.dtos.requests;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import yc.mhkif.aftas.entities.Fish;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class LevelRequest {
    @NotBlank(message = "Description is Required")
    @Size(min = 6, max = 250, message = "Name must be between 6 and 250 characters")
    private String description;

    @NotNull(message = "Level Points is Required")
    private Integer points;
}

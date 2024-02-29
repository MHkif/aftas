package yc.mhkif.aftas.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionRequest {


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Competition Date is Required")
    private LocalDate date;
    @DateTimeFormat(pattern = "HH:mm")
    @NotNull(message = "Competition Start Time is Required")
    private LocalTime startTime;

    @DateTimeFormat(pattern = "HH:mm")
    @NotNull(message = "Competition End Time is Required")
    private LocalTime endTime;

    private int numberOfParticipants = 0;

    @NotNull(message = "Competition Location is Required")
    private String location;

    @NotNull(message = "Competition Amount is Required")
    private Double amount;
}

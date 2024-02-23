package yc.mhkif.aftas.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import yc.mhkif.aftas.entities.User;
import yc.mhkif.aftas.enums.CompetitionStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionResponse {

    private String code;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;
    private int numberOfParticipants ;
    private String location;
    private Double amount;
    private CompetitionStatus status;
    private Collection<User> users = new ArrayList<>();

}

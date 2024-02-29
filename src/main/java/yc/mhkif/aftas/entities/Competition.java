package yc.mhkif.aftas.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import yc.mhkif.aftas.enums.CompetitionStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Data
@Entity
public class Competition {
    @Id
    private String code;
    @DateTimeFormat(pattern = "yyyy-MM-dd")

    private LocalDate date;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;
    private int numberOfParticipants;
    private String location;
    private Double amount;

    @Enumerated(EnumType.STRING)
    private CompetitionStatus status = CompetitionStatus.COMING;

    @ManyToMany
    @JoinTable(name = "Ranking",
            joinColumns = @JoinColumn(name = "competition_code"),
            inverseJoinColumns = @JoinColumn(name = "user_num"))
    @JsonIgnore
    private Collection<User> members = new ArrayList<>();

    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<Hunting> hunting = new ArrayList<>();


    public int getNumberOfParticipants() {
       return Objects.nonNull(members) ? members.size() : 0;
    }

}

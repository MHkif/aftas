package yc.mhkif.aftas.entities.implementations;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionUser implements Serializable {

    @Column(name = "user_num")
    private Integer userId;
    @Column(name = "competition_code")
    private String competitionCode;


}

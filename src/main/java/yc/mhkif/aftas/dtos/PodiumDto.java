package yc.mhkif.aftas.dtos;

import lombok.Data;
import yc.mhkif.aftas.dtos.responses.CompetitionResponse;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.entities.Member;

import java.util.ArrayList;
import java.util.List;

@Data
public class PodiumDto {
    private List<Member> topThree = new ArrayList<>(3);
    private CompetitionResponse competition;

}

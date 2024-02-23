package yc.mhkif.aftas.dto;

import lombok.Data;
import yc.mhkif.aftas.dto.responses.CompetitionResponse;
import yc.mhkif.aftas.entities.User;

import java.util.ArrayList;
import java.util.List;

@Data
public class PodiumDto {
    private List<User> topThree = new ArrayList<>(3);
    private CompetitionResponse competition;

}

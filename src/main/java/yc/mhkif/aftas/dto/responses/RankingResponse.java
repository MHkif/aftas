package yc.mhkif.aftas.dto.responses;

import lombok.Data;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.entities.User;

@Data
public class RankingResponse {

    private int rank;
    private int score;
    private User user;
    private Competition competition;
}

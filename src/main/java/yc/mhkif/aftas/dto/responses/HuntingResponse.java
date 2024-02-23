package yc.mhkif.aftas.dto.responses;

import lombok.Data;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.entities.Fish;
import yc.mhkif.aftas.entities.User;

@Data
public class HuntingResponse {
    private  int id;
    private int nomberOfFish;

    private User user;

    private Fish fish;

    private Competition competition;
}

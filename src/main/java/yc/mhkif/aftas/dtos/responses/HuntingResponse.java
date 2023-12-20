package yc.mhkif.aftas.dtos.responses;

import lombok.Data;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.entities.Fish;
import yc.mhkif.aftas.entities.Member;

@Data
public class HuntingResponse {
    private  int id;
    private int nomberOfFish;

    private Member member;

    private Fish fish;

    private Competition competition;
}

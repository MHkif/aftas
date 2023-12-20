package yc.mhkif.aftas.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.entities.Fish;
import yc.mhkif.aftas.entities.Member;

@Data
public class HuntingRequest {

    @NotNull(message = "Hunting at least Must implies on one fish.")
    private int nomberOfFish;

    @NotNull(message = "Member is required to do the hunting")
    private Member member;

    @NotNull(message = "Fish is required to do the hunting")
    private Fish fish;

    @NotNull(message = "Competition is required to do the hunting")
    private Competition competition;
}

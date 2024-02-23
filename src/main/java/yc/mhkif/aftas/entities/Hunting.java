package yc.mhkif.aftas.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Hunting{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int nomberOfFish;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "fish_id")
    private Fish fish;

    @ManyToOne()
    @JoinColumn(name = "competition_id")
    private Competition competition;

}

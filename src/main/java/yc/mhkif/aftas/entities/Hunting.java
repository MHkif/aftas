package yc.mhkif.aftas.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
@Data
@Entity
public class Hunting{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hunting_seq_generator")
    @SequenceGenerator(name = "hunting_seq_generator", sequenceName = "hunting_sequence", allocationSize = 1)
    private int id;
    private int nomberOfFish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fish_id")
    @JsonIgnore
    private Fish fish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_id")
    @JsonIgnore
    private Competition competition;

}

package yc.mhkif.aftas.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "level_seq_generator")
    @SequenceGenerator(name = "level_seq_generator", sequenceName = "level_sequence", allocationSize = 1)
    private Integer code;
    @Column(unique = true)
    private String description;
    private Integer points;
    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL)
    private Collection<Fish> fish = new ArrayList<>();
}

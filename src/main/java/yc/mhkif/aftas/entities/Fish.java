package yc.mhkif.aftas.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
public class Fish {
    @Id
    private String name;
    private Double averageWeight;
    @OneToMany(mappedBy = "fish", cascade = CascadeType.ALL)
    private Collection<Hunting> hunting = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "level_id")
    @JsonIgnore
    private Level level;
}

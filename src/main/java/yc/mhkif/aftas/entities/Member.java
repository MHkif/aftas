package yc.mhkif.aftas.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import yc.mhkif.aftas.enums.IdentityDocumentType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
    @SequenceGenerator(name = "member_seq_generator", sequenceName = "member_sequence", allocationSize = 1)
    private Integer num;

    private String name ;

    private String familtyName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime accessionDate;

    private String nationality;

    @Enumerated(EnumType.STRING)
    private IdentityDocumentType identityDocument;

    @Column(unique = true)
    private String identityNumber;

    @ManyToMany
    @JoinTable(name = "Ranking",
            joinColumns = @JoinColumn(name = "member_num"),
            inverseJoinColumns = @JoinColumn(name = "competition_code")
    )
    @JsonIgnore
    private Collection<Competition> competitions = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<Hunting> hunting = new ArrayList<>();

}

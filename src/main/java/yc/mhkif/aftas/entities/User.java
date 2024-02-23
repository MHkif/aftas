package yc.mhkif.aftas.entities;

import jakarta.persistence.*;
import lombok.Data;
import yc.mhkif.aftas.enums.IdentityDocumentType;
import yc.mhkif.aftas.enums.Role;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num;
    private String first_name ;
    private String last_name;
    private String email;
    private String password;
    private LocalDateTime accessionDate;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String nationality;
    @Enumerated(EnumType.STRING)
    private IdentityDocumentType identityDocument;

    @Column(unique = true)
    private String identityNumber;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Ranking",
            joinColumns = @JoinColumn(name = "user_num"),
            inverseJoinColumns = @JoinColumn(name = "competition_code")
    )
    private Collection<Competition> competitions = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private Collection<Hunting> hunting = new ArrayList<>();

}

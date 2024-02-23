package yc.mhkif.aftas.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.enums.IdentityDocumentType;
import yc.mhkif.aftas.enums.Role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
public class MemberResponse {
    private int num;
    private String first_name ;
    private String last_name;
    private String email;
    private String password;
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime accessionDate;
    private String nationality;
    private Role role;
    private IdentityDocumentType identityDocument;
    private String identityNumber;
    private Collection<Competition> competitions = new ArrayList<>();
}

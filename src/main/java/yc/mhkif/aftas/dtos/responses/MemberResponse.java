package yc.mhkif.aftas.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.enums.IdentityDocumentType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
public class MemberResponse {
    private int num;
    private String name ;
    private String familtyName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime accessionDate;
    private String nationality;
    private IdentityDocumentType identityDocument;
    private String identityNumber;

    @JsonIgnore
    private Collection<Competition> competitions = new ArrayList<>();
}

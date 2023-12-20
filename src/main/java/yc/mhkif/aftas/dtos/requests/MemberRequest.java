package yc.mhkif.aftas.dtos.requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import yc.mhkif.aftas.enums.IdentityDocumentType;

import java.time.LocalDateTime;


@Data
public class MemberRequest {

    @NotBlank(message = "Name is Required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name ;

    @NotBlank(message = "Last Name is Required")
    @Size(min = 3, max = 50, message = "Last Name must be between 3 and 50 characters")
    private String familtyName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime accessionDate = LocalDateTime.now();

    @NotBlank(message = "nationality is Required")
    @Size(min = 3, max = 50, message = "Nationality must be between 3 and 50 characters")
    private String nationality;

    @NotNull(message = "Identity Document Type is Required")
    private IdentityDocumentType identityDocument;

    @NotNull(message = "Identity Number is Required")
    @Size(min = 7, max = 8, message = "Identity Number must have 7 or 8 characters")
    private String identityNumber;

}

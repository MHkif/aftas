package yc.mhkif.aftas.dto.requests;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import yc.mhkif.aftas.enums.IdentityDocumentType;
import yc.mhkif.aftas.enums.Role;

import java.time.LocalDateTime;


@Data
public class UserRequest {

    @NotBlank(message = "First name is Required")
    @Size(min = 3, max = 20, message = "First name must be between 3 and 20 characters")
    private String first_name ;

    @NotBlank(message = "Last Name is Required")
    @Size(min = 3, max = 20, message = "Last Name must be between 3 and 20 characters")
    private String last_name;

    @NotBlank(message = "Email is Required")
    @Email
    private String email;

    @NotBlank(message = "Password is Required")
    @Size(min = 6, max = 26, message = "Email must be valid & between 6 and 26 characters")
    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime accessionDate = LocalDateTime.now();

    @NotBlank(message = "nationality is Required")
    @Size(min = 4, max = 20, message = "Nationality must be between 4 and 20 characters")
    private String nationality;

    @NotNull(message = "Identity Document Type is Required")
    private IdentityDocumentType identityDocument;
    private Role role = Role.MEMBER;
    private boolean isActivate = false;


    @NotNull(message = "Identity Number is Required")
    @Size(min = 7, max = 8, message = "Identity Number must have 7 or 8 characters")
    private String identityNumber;

}

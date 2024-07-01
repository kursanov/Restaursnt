package kursanov.dto.authDto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import kursanov.enums.Role;
import kursanov.validations.password.PasswordValidation;
import lombok.*;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class SignInRequest {

    @Email
    private String email;

    @PasswordValidation
    private String password;


}

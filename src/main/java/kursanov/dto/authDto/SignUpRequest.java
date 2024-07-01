package kursanov.dto.authDto;


import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import kursanov.enums.Role;
import kursanov.validations.dateOfBirth.DateOfBirthValidation;
import kursanov.validations.password.PasswordValidation;
import kursanov.validations.phoneNumber.PhoneNumberValidation;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SignUpRequest {

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @DateOfBirthValidation
    private LocalDate dateOfBirth;

    @Email
    private String email;

    @PasswordValidation
    private String password;

    @PhoneNumberValidation
    private String phoneNumber;

    private Role role;

    @Min(value = 1)
    private int experience;


}

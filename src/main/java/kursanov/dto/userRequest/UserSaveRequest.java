package kursanov.dto.userRequest;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import kursanov.entities.Restaurant;
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
@NoArgsConstructor
public class UserSaveRequest {

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

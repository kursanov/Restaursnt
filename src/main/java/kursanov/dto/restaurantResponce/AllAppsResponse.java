package kursanov.dto.restaurantResponce;

import kursanov.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AllAppsResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        int experience,
        LocalDate dateOfBirth,
        Role role
) {
}

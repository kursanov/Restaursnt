package kursanov.dto.userRequest;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ApplicationRequest(
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String email,
        String phoneNumber,
        int experience
) {
}

package kursanov.dto.menu;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.NonNull;

import java.math.BigDecimal;

@Builder
public record MenuRequest(
        @NonNull
        String name,
        @NonNull
        String image,
        @Min(value = 1)
        BigDecimal price,
        String description,
        boolean isVegetarian

) {
}

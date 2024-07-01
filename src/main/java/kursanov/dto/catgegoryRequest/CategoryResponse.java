package kursanov.dto.catgegoryRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
public record CategoryResponse(
        Long id,
        String name
) {
}

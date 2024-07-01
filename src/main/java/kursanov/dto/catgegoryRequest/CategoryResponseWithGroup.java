package kursanov.dto.catgegoryRequest;

import lombok.Builder;

import java.util.List;

@Builder
public record CategoryResponseWithGroup(
        String categoryName,
        List<String> subCategoryNames
) {
}

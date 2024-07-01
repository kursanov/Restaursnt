package kursanov.service;

import kursanov.dto.menu.SimpleResponse;
import kursanov.dto.catgegoryRequest.CategoryResponse;
import kursanov.dto.catgegoryRequest.CategoryResponseWithGroup;

import java.util.List;

public interface SubcategoryService {
    SimpleResponse save(Long catId, String name);

    List<CategoryResponse> getAll(Long catId);

    CategoryResponse getById(Long subCatId);

    SimpleResponse update(Long id, String name);

    SimpleResponse delete(Long subCatId);

    List<CategoryResponseWithGroup> getAllWithGroup();

}

package kursanov.service;

import kursanov.dto.menu.SimpleResponse;
import kursanov.dto.catgegoryRequest.CategoryResponse;

import java.util.List;

public interface CategoryService {

    SimpleResponse save(String name);

    SimpleResponse update(Long id, String name);


    SimpleResponse delete(Long id);

    List<CategoryResponse> getAll();

    CategoryResponse getById(Long catId);
}

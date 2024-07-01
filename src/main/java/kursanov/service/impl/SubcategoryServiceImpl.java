package kursanov.service.impl;

import jakarta.transaction.Transactional;
import kursanov.dto.menu.SimpleResponse;
import kursanov.dto.catgegoryRequest.CategoryResponse;
import kursanov.dto.catgegoryRequest.CategoryResponseWithGroup;
import kursanov.entities.Category;
import kursanov.entities.Restaurant;
import kursanov.entities.SubCategory;
import kursanov.entities.User;
import kursanov.repository.CategoryRepository;
import kursanov.repository.SubCategoryRepository;
import kursanov.repository.UserRepository;
import kursanov.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final UserRepository userRepository;

    @Override @Transactional
    public SimpleResponse save(Long catId, String name) {
        Category category = categoryRepository.getCategoryId(catId);
        SubCategory subCategory = new SubCategory();
        subCategory.setName(name);
        subCategory.setCategory(category);
        category.addSubCategory(subCategory);
        subCategoryRepository.save(subCategory);
        return new SimpleResponse(HttpStatus.OK, "success!!!");
    }

    @Override
    public List<CategoryResponse> getAll(Long catId) {
        Category category = categoryRepository.getCategoryId(catId);
        return subCategoryRepository.getAllSubCategoriesByCategoryId(category.getId());
    }

    @Override
    public CategoryResponse getById(Long subCatId) {
        subCategoryRepository.getSubById(subCatId);
        return subCategoryRepository.getSubCategoryById(subCatId);
    }

    @Override
    public SimpleResponse update(Long id, String name) {
        SubCategory subCategory = subCategoryRepository.getSubById(id);
        subCategory.setName(name);
        subCategoryRepository.save(subCategory);
        return new SimpleResponse(HttpStatus.OK, "success updated");
    }

    @Override
    public SimpleResponse delete(Long subCatId) {
        SubCategory subCategory = subCategoryRepository.getSubById(subCatId);
        subCategoryRepository.delete(subCategory);
        return new SimpleResponse(HttpStatus.OK, "success deleted");
    }

    @Override
    public List<CategoryResponseWithGroup> getAllWithGroup() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getByEmail(email);
        Restaurant restaurant = user.getRestaurant();
        List<CategoryResponse> categories = categoryRepository.getAllCategories(restaurant.getId());
        List<SubCategory> subCategories = subCategoryRepository.getAllSubByResId(restaurant.getId());

        Map<String, List<String>> filter = new HashMap<>();

        for (CategoryResponse category : categories) {
            filter.put(category.name(), new ArrayList<>());
        }

        for (SubCategory subCategory : subCategories) {
            String catName = subCategory.getCategory().getName();
            filter.get(catName).add(subCategory.getName());
        }

        return filter.entrySet().stream()
                .map(entry -> new CategoryResponseWithGroup(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}

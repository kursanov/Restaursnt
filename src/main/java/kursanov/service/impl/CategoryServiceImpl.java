package kursanov.service.impl;

import kursanov.dto.menu.SimpleResponse;
import kursanov.dto.catgegoryRequest.CategoryResponse;
import kursanov.entities.Category;
import kursanov.entities.User;
import kursanov.excaptions.NotFoundException;
import kursanov.repository.CategoryRepository;
import kursanov.repository.UserRepository;
import kursanov.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public SimpleResponse save(String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User chef = userRepository.getByEmail(email);
        Category category = new Category();
        category.setName(name);
        chef.getRestaurant().addCategory(category);
        category.setRestaurant(chef.getRestaurant());
        categoryRepository.save(category);
        return new SimpleResponse(HttpStatus.OK, "success!!!");
    }

    @Override
    public SimpleResponse update(Long id, String name) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new NotFoundException("This  category not  found!"));
        category.setName(name);
        categoryRepository.save(category);
        return new SimpleResponse(HttpStatus.OK,"Success updated!");
    }

    @Override
    public SimpleResponse delete(Long id) {
       Category category = categoryRepository.getCategoryId(id);
       categoryRepository.delete(category);
        return new SimpleResponse(HttpStatus.OK, "success deleted!!!");
    }

    @Override
    public List<CategoryResponse> getAll() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getByEmail(email);
        return categoryRepository.getAllCategories(user.getRestaurant().getId());
    }

    @Override
    public CategoryResponse getById(Long catId) {
        categoryRepository.getCategoryId(catId);
        return categoryRepository.getCategoryById(catId);
    }


}

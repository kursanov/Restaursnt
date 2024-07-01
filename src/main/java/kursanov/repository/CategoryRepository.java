package kursanov.repository;

import kursanov.dto.catgegoryRequest.CategoryResponse;
import kursanov.entities.Category;
import kursanov.excaptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  CategoryRepository extends JpaRepository< Category,Long> {
    default Category getCategoryId(Long id){
        return findById(id).orElseThrow(() ->
                new NotFoundException("Category with ID: "+id+" nor found"));
    }

    @Query("select new kursanov.dto.catgegoryRequest.CategoryResponse(c.id, c.name) from Category c where c.restaurant.id =:id")
    List<CategoryResponse> getAllCategories(Long id);

    @Query("select new kursanov.dto.catgegoryRequest.CategoryResponse(c.id, c.name) from Category c where c.id =:catId")
    CategoryResponse getCategoryById(Long catId);
}

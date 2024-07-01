package kursanov.repository;

import kursanov.dto.catgegoryRequest.CategoryResponse;
import kursanov.entities.Category;
import kursanov.entities.SubCategory;
import kursanov.excaptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {

    @Query("select new kursanov.dto.catgegoryRequest.CategoryResponse(s.id, s.name) from SubCategory s where s.category.id =:id order by s.name")
    List<CategoryResponse> getAllSubCategoriesByCategoryId(Long id);

    @Query("select new kursanov.dto.catgegoryRequest.CategoryResponse(s.id, s.name) from SubCategory s where s.id =:id")
    CategoryResponse getSubCategoryById(Long id);

    default SubCategory getSubById(Long id){
        return findById(id).orElseThrow(() ->
                new NotFoundException("SubCategory with ID: "+id+" not found"));
    }

    @Query("select s from SubCategory s where s.category.restaurant.id =:id")
    List<SubCategory> getAllSubByResId(Long id);
}

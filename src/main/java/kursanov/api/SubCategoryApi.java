package kursanov.api;

import kursanov.dto.menu.SimpleResponse;
import kursanov.dto.catgegoryRequest.CategoryResponse;
import kursanov.dto.catgegoryRequest.CategoryResponseWithGroup;
import kursanov.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subCat")
@RequiredArgsConstructor
public class SubCategoryApi {

    private final SubcategoryService service;

    @Secured({"CHEF"})
    @PostMapping("/{catId}")
    public SimpleResponse save(@PathVariable Long catId,
                               @RequestParam String name) {
        return service.save(catId, name);
    }

    @Secured({"CHEF", "WAITER"})
    @GetMapping("/{catId}")
    public List<CategoryResponse> getAll(@PathVariable Long catId) {
        return service.getAll(catId);
    }

    @Secured({"CHEF", "WAITER"})
    @GetMapping("/group")
    public List<CategoryResponseWithGroup> getAllWithGroup() {
        return service.getAllWithGroup();
    }

    @Secured({"CHEF", "WAITER"})
    @GetMapping("/by-id/{subCatId}")
    public CategoryResponse getById(@PathVariable Long subCatId) {
        return service.getById(subCatId);
    }

    @Secured({"CHEF"})
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id,
                                 @RequestParam String name) {
        return service.update(id,name);
    }

    @Secured({"CHEF"})
    @DeleteMapping("/{subCatId}")
    public SimpleResponse delete(@PathVariable Long subCatId) {
        return service.delete(subCatId);
    }
}

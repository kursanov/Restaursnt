package kursanov.api;

import kursanov.dto.menu.SimpleResponse;
import kursanov.dto.catgegoryRequest.CategoryResponse;
import kursanov.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cat")
@RequiredArgsConstructor
public class CategoryApi {

    private final CategoryService categoryService;

    @Secured({"CHEF"})
    @PostMapping
    public SimpleResponse save(@RequestParam("name") String name) {
        return categoryService.save(name);
    }

    @Secured({"CHEF", "WAITER"})
    @GetMapping
    public List<CategoryResponse> getAll() {
        return categoryService.getAll();
    }

    @Secured({"CHEF", "WAITER"})
    @GetMapping("/by-id/{catId}")
    public CategoryResponse getById(@PathVariable Long catId) {
        return categoryService.getById(catId);
    }

    @Secured({"CHEF"})
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id, @RequestParam String name) {
        return categoryService.update(id,name);
    }

    @Secured({"CHEF"})
    @DeleteMapping("/{catId}")
    public SimpleResponse delete(@PathVariable Long catId) {
        return categoryService.delete(catId);
    }

}

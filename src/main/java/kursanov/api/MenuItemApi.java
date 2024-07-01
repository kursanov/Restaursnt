package kursanov.api;

import jakarta.validation.Valid;
import kursanov.dto.menu.MenuRequest;
import kursanov.dto.menu.MenuResponse;
import kursanov.dto.menu.SimpleResponse;
import kursanov.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Queue;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuItemApi {

    private final MenuService menuService;


    @Secured({"CHEF"})
    @PostMapping("/save/{subId}")
    public SimpleResponse save(@PathVariable Long subId,
                               @RequestBody @Valid MenuRequest menuRequest) {
        return menuService.save(subId, menuRequest);
    }

    @Secured({"CHEF", "WAITER"})
    @GetMapping("/{subId}")
    public Queue<MenuResponse> getAllMenus(@PathVariable Long subId) {
        return menuService.getAllMenus(subId);
    }

    @Secured({"CHEF", "WAITER"})
    @GetMapping("/by-id/{menuId}")
    public MenuResponse getMenuById(@PathVariable Long menuId) {
        return menuService.getMenuById(menuId);
    }

    @Secured({"CHEF", "WAITER"})
    @PutMapping("/{menuId}")
    public SimpleResponse update(@PathVariable Long menuId,
                                 @RequestBody MenuRequest menuRequest) {
        return menuService.update(menuId, menuRequest);
    }

    @Secured({"CHEF", "WAITER"})
    @DeleteMapping("/{menuId}")
    public SimpleResponse delete(@PathVariable Long menuId) {
        return menuService.delete(menuId);
    }

    @Secured({"CHEF", "WAITER"})
    @GetMapping("/search")
    public List<MenuResponse> globalSearch(@RequestParam("word") String word) {
        return menuService.globalSearch(word);
    }

    @Secured({"CHEF", "WAITER"})
    @GetMapping("/asc")
    public List<MenuResponse> menuOrderBy() {
        return menuService.menuOrderBy();
    }

    @Secured({"CHEF", "WAITER"})
    @GetMapping("/desc")
    public List<MenuResponse> menuOrderByDesc() {
        return menuService.menuOrderByDesc();
    }

    @Secured({"CHEF", "WAITER"})
    @GetMapping("/isVega")
    public List<MenuResponse> isVega(@RequestParam boolean isVega) {
        return menuService.isVega(isVega);
    }


}

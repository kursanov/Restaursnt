package kursanov.service.impl;

import jakarta.transaction.Transactional;
import kursanov.dto.menu.MenuRequest;
import kursanov.dto.menu.MenuResponse;
import kursanov.dto.menu.SimpleResponse;
import kursanov.entities.Menu;
import kursanov.entities.SubCategory;
import kursanov.entities.User;
import kursanov.repository.MenuRepository;
import kursanov.repository.SubCategoryRepository;
import kursanov.repository.UserRepository;
import kursanov.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final UserRepository userRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final MenuRepository menuRepository;

    @Override @Transactional
    public SimpleResponse save(Long subId, MenuRequest menuRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User chef = userRepository.getByEmail(email);
        SubCategory subCategory = subCategoryRepository.getSubById(subId);
        Menu menu = new Menu();
        menu.setName(menuRequest.name());
        menu.setImage(menuRequest.image());
        menu.setPrice(menuRequest.price());
        menu.setDescription(menuRequest.description());
        menu.setVegetarian(menuRequest.isVegetarian());
        menuRepository.save(menu);
        chef.getRestaurant().addMenu(menu);
        menu.setRestaurant(chef.getRestaurant());
        subCategory.addMenu(menu);
        menu.setSubcategory(subCategory);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("success saved")
                .build();
    }

    @Override
    public Queue<MenuResponse> getAllMenus(Long subId) {
        List<MenuResponse> menuList = (List<MenuResponse>) menuRepository.getAllMenus(subId);
        return new LinkedList<>(menuList);
    }

    @Override
    public MenuResponse getMenuById(Long menuId) {
        menuRepository.getMenuById(menuId);
        return menuRepository.getMenuResponse(menuId);
    }

    @Override
    public SimpleResponse update(Long menuId, MenuRequest menuRequest) {
        Menu menu = menuRepository.getMenuById(menuId);
        menu.setName(menuRequest.name());
        menu.setImage(menuRequest.image());
        menu.setPrice(menuRequest.price());
        menu.setDescription(menuRequest.description());
        menu.setVegetarian(menuRequest.isVegetarian());
        menuRepository.save(menu);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("success updated")
                .build();
    }

    @Override
    public SimpleResponse delete(Long menuId) {
        Menu menu = menuRepository.getMenuById(menuId);
        menuRepository.delete(menu);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("success deleted")
                .build();
    }

    @Override
    public List<MenuResponse> globalSearch(String word) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getByEmail(email);
        List<MenuResponse> menuResponses = menuRepository.globalSearch(user.getRestaurant().getId(), "%"+word+"%");
        System.out.println("word before = " + word);
        for (MenuResponse menuRespons : menuResponses) {
            System.out.println("menuRespons.name() = " + menuRespons.name());
        }
        System.out.println("word after = " + word);
        return menuRepository.globalSearch(user.getRestaurant().getId(), "%"+word+"%");
    }

    @Override
    public List<MenuResponse> menuOrderBy() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getByEmail(email);
        return menuRepository.menuOrderBy(user.getRestaurant().getId());
    }

    @Override
    public List<MenuResponse> menuOrderByDesc() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getByEmail(email);
        return menuRepository.menuOrderByDesc(user.getRestaurant().getId());
    }

    @Override
    public List<MenuResponse> isVega(boolean isVega) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getByEmail(email);
        return menuRepository.isVega(user.getRestaurant().getId(), isVega);
    }


}

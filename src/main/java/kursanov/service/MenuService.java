package kursanov.service;

import kursanov.dto.menu.MenuRequest;
import kursanov.dto.menu.MenuResponse;
import kursanov.dto.menu.SimpleResponse;

import java.util.List;
import java.util.Queue;

public interface MenuService {

    SimpleResponse save(Long subId, MenuRequest menuRequest);

    Queue<MenuResponse> getAllMenus(Long subId);

    MenuResponse getMenuById(Long menuId);

    SimpleResponse update(Long menuId, MenuRequest menuRequest);

    SimpleResponse delete(Long menuId);

    List<MenuResponse> globalSearch(String word);

    List<MenuResponse> menuOrderBy();

    List<MenuResponse> menuOrderByDesc();

    List<MenuResponse> isVega(boolean isVega);
}

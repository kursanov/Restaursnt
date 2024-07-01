package kursanov.repository;

import kursanov.dto.menu.MenuResponse;
import kursanov.entities.Menu;
import kursanov.excaptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Queue;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {

    @Query("select new kursanov.dto.menu.MenuResponse(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) from Menu m where m.subcategory.id = :id and m.stopList is null")
    Queue<MenuResponse> getAllMenus(Long id);


    default Menu getMenuById(Long menuId){
        return findById(menuId).orElseThrow(() ->
                new NotFoundException("Menu with ID: "+menuId+" not found"));
    }

    @Query("select new kursanov.dto.menu.MenuResponse(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) from Menu m where m.id =:menuId and m.stopList is null ")
    MenuResponse getMenuResponse(Long menuId);

    @Query("""
            select new kursanov.dto.menu.MenuResponse(m.id, m.name, m.image, m.price, m.description, m.isVegetarian)
            from Menu m join StopList s on m.stopList.id = s.id
            where m.restaurant.id = :id and m.stopList.id = null
            and m.stopList is null
            and (lower(m.name) like lower(:word)
                or lower(m.description) like lower(:word)
                or lower(m.image) like lower(:word)
                or lower(m.subcategory.name) like lower(:word)
                or lower(m.subcategory.category.name) like lower(:word))
            """)
    List<MenuResponse> globalSearch(@Param("id") Long id, @Param("word") String word);

    @Query("select new kursanov.dto.menu.MenuResponse(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) from Menu m where m.restaurant.id =:id and m.stopList is null order by m.price")
    List<MenuResponse> menuOrderBy(Long id);

    @Query("select new kursanov.dto.menu.MenuResponse(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) from Menu m where m.restaurant.id =:id and m.stopList is null order by m.price desc")
    List<MenuResponse> menuOrderByDesc(Long id);

    @Query("select new kursanov.dto.menu.MenuResponse(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) from Menu m where m.restaurant.id =:id and m.isVegetarian in (:isVega) and m.stopList is null ")
    List<MenuResponse> isVega(Long id, boolean isVega);

    @Query("select m from Menu m where m.name = :name")
    Menu findByName(String name);
}

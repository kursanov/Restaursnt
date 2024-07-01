package kursanov.api;

import jakarta.validation.Valid;
import kursanov.dto.menu.SimpleResponse;
import kursanov.dto.restaurantRequest.SaveRestaurantRequest;
import kursanov.dto.restaurantRequest.UpdateRestRequest;
import kursanov.dto.restaurantResponce.RestaurantResponse;
import kursanov.entities.Restaurant;
import kursanov.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class RestaurantApi {

    private final RestaurantService restaurantService;

    @Secured("ADMIN")
    @PostMapping("/save")
    public SimpleResponse save(@RequestBody @Valid SaveRestaurantRequest saveRestaurantRequest) {
        return restaurantService.saveRestaurant(saveRestaurantRequest);
    }

    @GetMapping("/findId/{id}")
    public RestaurantResponse findId(@PathVariable Long id) {
        return restaurantService.findById(id);
    }

    @GetMapping("/get-all")
    public List<RestaurantResponse> findAll() {
        return restaurantService.getAllRestaurants();
    }

    @Secured("ADMIN")
    @PutMapping("/update/{id}")
    public SimpleResponse update(@PathVariable Long id, @RequestBody UpdateRestRequest updateRestRequest) {
        return restaurantService.update(id, updateRestRequest);
    }

    @Secured("ADMIN")
    @DeleteMapping("/delete/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
       return restaurantService.delete(id);
    }

}

package kursanov.repository;

import kursanov.dto.restaurantResponce.RestaurantResponse;
import kursanov.entities.Restaurant;
import kursanov.excaptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long > {

    default Restaurant getRestaurantById(Long restId){
        return findById(restId).orElseThrow(() ->
                new NotFoundException("restaurant with ID: "+restId+" not found!!!"));
    }

    @Query("select new kursanov.dto.restaurantResponce.RestaurantResponse(r.name, r.location, r.resType, r.numberOfEmployees, r.service) from Restaurant r")
    List<RestaurantResponse> getAllRestaurants();
}

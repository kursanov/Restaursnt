package kursanov.service;

import kursanov.dto.menu.SimpleResponse;
import kursanov.dto.restaurantRequest.SaveRestaurantRequest;
import kursanov.dto.restaurantRequest.UpdateRestRequest;
import kursanov.dto.restaurantResponce.RestaurantResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestaurantService {



    SimpleResponse saveRestaurant(SaveRestaurantRequest saveRestaurantRequest);


    RestaurantResponse findById(Long id);


    List<RestaurantResponse> getAllRestaurants();

    SimpleResponse update(Long id, UpdateRestRequest updateRestRequest);

    SimpleResponse delete (Long id);






}

package kursanov.service.impl;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import kursanov.dto.menu.SimpleResponse;
import kursanov.dto.restaurantRequest.SaveRestaurantRequest;
import kursanov.dto.restaurantRequest.UpdateRestRequest;
import kursanov.dto.restaurantResponce.RestaurantResponse;
import kursanov.entities.Restaurant;
import kursanov.entities.User;
import kursanov.repository.RestaurantRepository;
import kursanov.repository.UserRepository;
import kursanov.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantServiceImpl  implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.getByEmail(email);
    }

    @Override
    @Transactional
    public SimpleResponse saveRestaurant(SaveRestaurantRequest saveRestaurantRequest) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(saveRestaurantRequest.getName());
        restaurant.setLocation(saveRestaurantRequest.getLocation());
        restaurant.setResType(saveRestaurantRequest.getResType());
        restaurant.setService(saveRestaurantRequest.getService());
        restaurantRepository.save(restaurant);
        return new SimpleResponse(HttpStatus.OK,"Success!");
    }

    @Override
    public RestaurantResponse findById(Long id) {
         Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("This restaurant not  found!"));
         return new RestaurantResponse(restaurant.getName(),restaurant.getLocation(),restaurant.getResType(),restaurant.getNumberOfEmployees(),restaurant.getService());
    }

    @Override
    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantRepository.getAllRestaurants();
    }

    @Override @Transactional
    public SimpleResponse update(Long id, UpdateRestRequest updateRestRequest) {
        Restaurant byId = restaurantRepository.getRestaurantById(id);

        byId.setName(updateRestRequest.getName());
        byId.setLocation(updateRestRequest.getLocation());
        byId.setResType(updateRestRequest.getResType());
        byId.setService(updateRestRequest.getService());

        restaurantRepository.save(byId);
        return new SimpleResponse(HttpStatus.OK,"Success!");
    }

    @Override @Transactional
    public SimpleResponse delete(Long id) {
        Restaurant restaurant = restaurantRepository.getRestaurantById(id);
        restaurantRepository.delete(restaurant);
        return new SimpleResponse(HttpStatus.OK,"Success deleted!");
    }
}

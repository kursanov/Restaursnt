package kursanov.dto.restaurantResponce;

import kursanov.entities.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

public class RestaurantResponse {

    private String name;

    private String location;

    private String resType;

    private int numberOfEmployees;

    private int service;

    public RestaurantResponse(String name, String location, String resType, int numberOfEmployees, int service) {
        this.name = name;
        this.location = location;
        this.resType = resType;
        this.numberOfEmployees = numberOfEmployees;
        this.service = service;
    }
}

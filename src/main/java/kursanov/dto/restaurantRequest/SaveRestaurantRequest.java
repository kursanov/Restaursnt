package kursanov.dto.restaurantRequest;

import lombok.*;



@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveRestaurantRequest {

    private String name;

    private String location;

    private String resType;

    private int service;
}

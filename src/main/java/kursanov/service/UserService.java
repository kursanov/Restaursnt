package kursanov.service;

import kursanov.dto.menu.SimpleResponse;
import kursanov.dto.restaurantResponce.AllAppsResponse;
import kursanov.dto.userRequest.ApplicationRequest;
import kursanov.dto.userRequest.UserSaveRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {


    SimpleResponse saveUserToRestaurant(Long restaurantID, UserSaveRequest userSaveRequest);

    SimpleResponse saveUser(Long id, ApplicationRequest applicationRequest);


    SimpleResponse assignToRestaurant(Long appId);


    SimpleResponse rejectFromApps(Long appId);


    List<AllAppsResponse> getAllApps(Long resId);

    AllAppsResponse getAppsById(Long appId);

    List<AllAppsResponse> getAllUsers(Long resId);

    AllAppsResponse getUserById(Long userId);
}

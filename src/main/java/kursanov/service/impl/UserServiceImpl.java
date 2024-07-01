package kursanov.service.impl;


import jakarta.transaction.Transactional;
import kursanov.dto.menu.SimpleResponse;
import kursanov.dto.restaurantResponce.AllAppsResponse;
import kursanov.dto.userRequest.ApplicationRequest;
import kursanov.dto.userRequest.UserSaveRequest;
import kursanov.entities.Application;
import kursanov.entities.Restaurant;
import kursanov.entities.User;
import kursanov.excaptions.AlreadyException;
import kursanov.repository.ApplicationRepository;
import kursanov.repository.RestaurantRepository;
import kursanov.repository.UserRepository;
import kursanov.service.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.rmi.AlreadyBoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Builder
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestaurantRepository restaurantRepository;
    private final ApplicationRepository applicationRepository;

    private void checkEmail(String email){
        boolean b = userRepository.existsByEmail(email);
        if (b) throw new AlreadyException("user with email: "+email+" already exists");
    }

    @Override @Transactional
    public SimpleResponse saveUserToRestaurant(Long restaurantID, UserSaveRequest userSaveRequest) {
        checkEmail(userSaveRequest.getEmail());
        Restaurant restaurant = restaurantRepository.findById(restaurantID).orElseThrow(() -> new RuntimeException("s"));

        User user = new User();
        user.setFirstName(userSaveRequest.getFirstName());
        user.setLastName(userSaveRequest.getLastName());
        user.setDateOfBirth(userSaveRequest.getDateOfBirth());
        user.setPassword(passwordEncoder.encode(userSaveRequest.getPassword()));
        user.setEmail(userSaveRequest.getEmail());
        user.setPhoneNumber(userSaveRequest.getPhoneNumber());
        user.setRole(userSaveRequest.getRole());
        user.setExperience(userSaveRequest.getExperience());

        if (restaurant.getUsers().size() < 15){
            user.setRestaurant(restaurant);
            restaurant.addUser(user);
            userRepository.save(user);
            restaurant.setNumberOfEmployees(restaurant.getNumberOfEmployees()+1);
        }else try {
            throw new AlreadyBoundException("Error");
        } catch (AlreadyBoundException e) {
            throw new RuntimeException(e);
        }

        return new SimpleResponse(HttpStatus.OK,"Success!");
    }

    @Override
    public SimpleResponse saveUser(Long id, ApplicationRequest applicationRequest) {
        checkEmail(applicationRequest.email());
//        Restaurant restaurant = restaurantRepository.findById(restaurantID).orElseThrow(() -> new RuntimeException("s"));
//
//        User user = new User();
//        user.setFirstName(userSaveRequest.getFirstName());
//        user.setLastName(userSaveRequest.getLastName());
//        user.setDateOfBirth(userSaveRequest.getDateOfBirth());
//        user.setPassword(userSaveRequest.getPassword());
//        user.setEmail(userSaveRequest.getEmail());
//        user.setPhoneNumber(userSaveRequest.getPhoneNumber());
//        user.setRole(userSaveRequest.getRole());
//        user.setExperience(userSaveRequest.getExperience());
//
//        if (restaurant.getUsers().size() < 15){
//            user.setRestaurant(restaurant);
//            restaurant.addUser(user);
//            userRepository.save(user);
//            restaurant.setNumberOfEmployees(restaurant.getNumberOfEmployees()+1);
//        }else try {
//            throw new AlreadyBoundException("Error");
//        } catch (AlreadyBoundException e) {
//            throw new RuntimeException(e);
//        }

        return new SimpleResponse(HttpStatus.OK,"Success!");
    }

    @Override @Transactional
    public SimpleResponse assignToRestaurant(Long appId) {

        Application application = applicationRepository.getAppById(appId);
        Restaurant restaurant = application.getRestaurant();

        if (restaurant.getNumberOfEmployees() > 15){
            throw new AlreadyException("There are no vacancies");
        }

        User user = new User();
        user.setFirstName(application.getFirstName());
        user.setLastName(application.getLastName());
        user.setEmail(application.getEmail());
        user.setPassword(application.getPassword());
        user.setPhoneNumber(application.getPhoneNumber());
        user.setExperience(application.getExperience());
        user.setDateOfBirth(application.getDateOfBirth());
        user.setRole(application.getRole());
        user.setRestaurant(restaurant);
        restaurant.addUser(user);

        userRepository.save(user);
        applicationRepository.delete(application);

        return new SimpleResponse(HttpStatus.OK,"yot are successfully hiring");
    }

    @Override
    public SimpleResponse rejectFromApps(Long appId) {
        Application application = applicationRepository.getAppById(appId);
        applicationRepository.delete(application);
        return new SimpleResponse(HttpStatus.OK,"We won't be able to hire you.");
    }

    @Override
    public List<AllAppsResponse> getAllApps(Long resId) {
        return applicationRepository.getAllApps(resId);
    }

    @Override
    public AllAppsResponse getAppsById(Long appId) {
        return applicationRepository.getAppsById(appId);
    }

    @Override
    public List<AllAppsResponse> getAllUsers(Long resId) {
        return userRepository.getAllUsers(resId);
    }

    @Override
    public AllAppsResponse getUserById(Long userId) {
        return userRepository.getUserById(userId);
    }
}

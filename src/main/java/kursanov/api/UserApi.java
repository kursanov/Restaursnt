package kursanov.api;

import jakarta.validation.Valid;
import kursanov.dto.menu.SimpleResponse;
import kursanov.dto.restaurantResponce.AllAppsResponse;
import kursanov.dto.userRequest.UserSaveRequest;
import kursanov.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;


    @Secured("ADMIN")
    @PostMapping("/saveToRestaurant/{restaurantId}")
    SimpleResponse saveUserToRestaurant(@PathVariable Long restaurantId,
                                        @RequestBody @Valid UserSaveRequest userSaveRequest){
        return userService.saveUserToRestaurant(restaurantId,userSaveRequest);
    }

    @Secured("ADMIN")
    @PostMapping("/hire/{appId}")
    SimpleResponse assignToRestaurant(@PathVariable Long appId){
        return userService.assignToRestaurant(appId);
    }

    @Secured("ADMIN")
    @PostMapping("/reject/{appId}")
    SimpleResponse rejectFromApps(@PathVariable Long appId){
        return userService.rejectFromApps(appId);
    }

    @Secured("ADMIN")
    @PostMapping("/all-apps/{resId}")
    public List<AllAppsResponse> getAllApps(@PathVariable Long resId){
        return userService.getAllApps(resId);
    }

    @Secured("ADMIN")
    @PostMapping("/by-id-apps/{appId}")
    AllAppsResponse getAppsById(@PathVariable Long appId){
        return userService.getAppsById(appId);
    }

    @Secured({"ADMIN", "CHEF", "WAITER"})
    @PostMapping("/get-all/{resId}")
    public List<AllAppsResponse> getAllUsers(@PathVariable Long resId){
        return userService.getAllUsers(resId);
    }

    @Secured({"ADMIN", "CHEF", "WAITER"})
    @PostMapping("/by-id/{userId}")
    AllAppsResponse getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }
}

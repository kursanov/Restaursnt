package kursanov.repository;

import kursanov.dto.restaurantResponce.AllAppsResponse;
import kursanov.entities.Application;
import kursanov.excaptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    default Application getAppById(Long appId){
        return findById(appId).orElseThrow(() ->
                new NotFoundException("Apps with ID: "+appId+" nor found"));
    }

    @Query("select new kursanov.dto.restaurantResponce.AllAppsResponse(a.id, a.firstName, a.lastName, a.email, a.phoneNumber, a.experience, a.dateOfBirth, a.role) " +
            "from Application a where a.restaurant.id =:resId")
    List<AllAppsResponse> getAllApps(Long resId);

    @Query("select new kursanov.dto.restaurantResponce.AllAppsResponse(a.id, a.firstName, a.lastName, a.email, a.phoneNumber, a.experience, a.dateOfBirth, a.role) " +
            "from Application a where a.id =:appId")
    AllAppsResponse getAppsById(Long appId);
}

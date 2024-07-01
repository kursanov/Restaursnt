package kursanov.repository;

import kursanov.dto.restaurantResponce.AllAppsResponse;
import kursanov.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> getUserByEmail(String email);

    boolean existsByEmail(String email);


   default User getByEmail(String email){
       return getUserByEmail(email).orElseThrow(() ->
               new RuntimeException("user with email: "+email+" npt found"));
   }

    @Query("select new kursanov.dto.restaurantResponce.AllAppsResponse(u.id, u.firstName, u.lastName, u.email, u.phoneNumber, u.experience, u.dateOfBirth, u.role) " +
            "from User u where u.restaurant.id =:resId")
    List<AllAppsResponse> getAllUsers(Long resId);

    @Query("select new kursanov.dto.restaurantResponce.AllAppsResponse(u.id, u.firstName, u.lastName, u.email, u.phoneNumber, u.experience, u.dateOfBirth, u.role) " +
            "from User u where u.id =:userId")
    AllAppsResponse getUserById(Long userId);
}

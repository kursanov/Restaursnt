package kursanov.repository;

import kursanov.entities.StopList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopListRepository extends JpaRepository<StopList,Long> {
}

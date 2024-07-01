package kursanov.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import kursanov.dto.menu.MenuResponse;
import kursanov.dto.menu.SimpleResponse;
import kursanov.dto.stopList.SaveStopListRequest;
import kursanov.dto.stopList.StopListResponse;
import kursanov.entities.Menu;
import kursanov.entities.StopList;
import kursanov.excaptions.NotFoundException;
import kursanov.repository.MenuRepository;
import kursanov.repository.StopListRepository;
import kursanov.service.StopListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StopListServiceImpl  implements StopListService {

    private final StopListRepository stopListRepository;
    private final MenuRepository menuRepository;



    @Override
    @Transactional
    public SimpleResponse save(String tamakName, SaveStopListRequest saveStopListRequest) {
        Menu menu= menuRepository.findByName(tamakName);

        StopList stopList = new StopList();

        stopList.setReason(saveStopListRequest.getReason());
        stopList.setDate(LocalDate.now());
        stopList.setMenu(menu);
        menu.setStopList(stopList);
        stopListRepository.save(stopList);
        return new SimpleResponse(HttpStatus.OK, "Menu item added to stop list successfully");
    }

    @Override
    public SimpleResponse update(Long id) {
        return null;
    }

    @Override
    public StopListResponse findById(Long id) {
        StopList stopList = stopListRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Jok!"));
        return new StopListResponse(stopList);
    }
    @Override
    public SimpleResponse delete(Long id) {
        if (!stopListRepository.existsById(id)) {
            throw new EntityNotFoundException("StopList with ID " + id + " not found!");
        }
        stopListRepository.deleteById(id);
        return new SimpleResponse(HttpStatus.OK, "Success!");
    }
}

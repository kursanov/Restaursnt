package kursanov.service;

import kursanov.dto.menu.SimpleResponse;
import kursanov.dto.stopList.SaveStopListRequest;
import kursanov.dto.stopList.StopListResponse;
import org.springframework.stereotype.Service;

@Service
public interface StopListService {



    SimpleResponse save(String tamakName,SaveStopListRequest saveStopListRequest);

    SimpleResponse update(Long id);


    StopListResponse findById(Long id);


    SimpleResponse delete(Long id);
}

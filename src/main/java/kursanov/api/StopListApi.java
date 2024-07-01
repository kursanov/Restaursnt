package kursanov.api;


import jakarta.validation.Valid;
import kursanov.dto.menu.SimpleResponse;
import kursanov.dto.stopList.SaveStopListRequest;
import kursanov.dto.stopList.StopListResponse;
import kursanov.service.StopListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stopList")
@RequiredArgsConstructor
public class StopListApi {

    private final StopListService stopListService;


    @Secured("CHEF")
    @PostMapping("/save/{tamakName}")
    SimpleResponse save(@PathVariable String tamakName, @Valid @RequestBody SaveStopListRequest saveStopListRequest) {
        return stopListService.save(tamakName, saveStopListRequest);
    }


    @GetMapping("/findId/{id}")
    StopListResponse findById(@PathVariable Long id) {
        return stopListService.findById(id);
    }


    @Secured("CHEF")
    @DeleteMapping("/delete/{id}")
    SimpleResponse delete(@PathVariable Long id) {
        return stopListService.delete(id);

    }
}

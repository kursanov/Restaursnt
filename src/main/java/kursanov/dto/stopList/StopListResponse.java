package kursanov.dto.stopList;


import kursanov.entities.StopList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class StopListResponse{

    private  String reason;
   private LocalDate date;



    public StopListResponse(StopList stopList) {
        this.reason = stopList.getReason();
        this.date = stopList.getDate();
    }
}

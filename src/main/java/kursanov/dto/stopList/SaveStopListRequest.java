package kursanov.dto.stopList;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveStopListRequest {

    private String reason;

    private LocalDate date;

}

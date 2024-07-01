package kursanov.dto.cheqy;


import kursanov.entities.Menu;
import kursanov.entities.User;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveChequeRequest {
    private List<Long> ids;
}

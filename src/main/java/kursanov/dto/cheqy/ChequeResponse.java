package kursanov.dto.cheqy;


import kursanov.dto.menu.MenuResponse;
import kursanov.entities.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ChequeResponse {


    private String waiterName;

    List<MenuResponse> tamaktary;

    private BigDecimal avaragePrice;

    private int service;

    private BigDecimal grandTotal;

}

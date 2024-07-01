package kursanov.api;


import kursanov.dto.cheqy.ChequeResponse;
import kursanov.service.ChequeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cheque")
@RequiredArgsConstructor
public class ChequeApi {

    private final ChequeService chequeService;

    @Secured({"CHEF" , "WAITER"})
    @PostMapping("/save")
    ChequeResponse save(@RequestParam List<Long> ids){
        return chequeService.save(ids);
    }

    @Secured({"WAITER"})
    @PostMapping("/ern-money-on-day")
    BigDecimal ernMoneyWaiter(@RequestParam LocalDate date){
        return chequeService.ernMoneyWaiter(date);
    }

    @Secured({"CHEF"})
    @PostMapping("/for-day")
    BigDecimal chequeForDay(@RequestParam LocalDate date){
        return chequeService.chequeForDay(date);
    }
}

package kursanov.service;

import kursanov.dto.cheqy.ChequeResponse;
import kursanov.dto.cheqy.SaveChequeRequest;
import kursanov.dto.menu.SimpleResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public interface ChequeService {


    ChequeResponse save(List<Long> ids
    );

    BigDecimal ernMoneyWaiter(LocalDate date);

    BigDecimal chequeForDay(LocalDate date);
}

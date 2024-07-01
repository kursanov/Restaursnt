package kursanov.service.impl;

import jakarta.transaction.Transactional;
import kursanov.dto.cheqy.ChequeResponse;
import kursanov.dto.menu.MenuResponse;
import kursanov.dto.menu.SimpleResponse;
import kursanov.entities.Cheque;
import kursanov.entities.Menu;
import kursanov.entities.User;
import kursanov.enums.Role;
import kursanov.excaptions.BedRequestException;
import kursanov.excaptions.ForbiddenException;
import kursanov.repository.ChequeRepository;
import kursanov.repository.MenuRepository;
import kursanov.repository.UserRepository;
import kursanov.service.ChequeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ChequeServiceImpl implements ChequeService {

    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;


    @Override
    @Transactional
    public ChequeResponse save(List<Long> ids) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getByEmail(email);

        List<MenuResponse> menuResponses = new ArrayList<>();

        BigDecimal price = BigDecimal.ZERO;
        Cheque cheque = new Cheque();
        cheque.setUser(user);
        user.addCheque(cheque);
        for (Long id : ids) {
            Menu menu = menuRepository.getMenuById(id);
            price = price.add(menu.getPrice());
            menu.addCheque(cheque);
            cheque.addMenu(menu);
            MenuResponse response = new MenuResponse(
                    menu.getId(), menu.getName(), menu.getImage(),
                    menu.getPrice(), menu.getDescription(), menu.isVegetarian()
            );
            menuResponses.add(response);
        }

        BigDecimal priceAverage = price.divide(BigDecimal.valueOf(ids.size()), RoundingMode.HALF_UP);
        cheque.setPriceAverage(price);

        chequeRepository.save(cheque);

        int servicePercent = user.getRestaurant().getService();
        BigDecimal serviceAmount = price.multiply(BigDecimal.valueOf(servicePercent / 100.0));
        BigDecimal grandTotalPrice = price.add(serviceAmount);

        return ChequeResponse.builder()
                .waiterName(user.getFirstName()+" "+user.getLastName())
                .tamaktary(menuResponses)
                .avaragePrice(priceAverage)
                .service(user.getRestaurant().getService())
                .grandTotal(grandTotalPrice)
                .build();
    }

    @Override
    public BigDecimal ernMoneyWaiter(LocalDate date) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getByEmail(email);
        if (!user.getRole().equals(Role.WAITER)){
            throw new ForbiddenException("error");
        }
        List<Cheque> cheques = user.getCheques();
        BigDecimal totalSum = BigDecimal.ZERO;
        LocalDate now = LocalDate.now();
        if (date.isAfter(now)){
            throw new BedRequestException("cannot be in the future");
        }

        for (Cheque cheque : cheques) {
            LocalDate createdAt = cheque.getCreated();
            if (createdAt.equals(date)){
                totalSum = totalSum.add(cheque.getPriceAverage());
            }
        }
        return totalSum;
    }

    @Override
    public BigDecimal chequeForDay(LocalDate date) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getByEmail(email);
        if (!user.getRole().equals(Role.CHEF)){
            throw new ForbiddenException("error");
        }

        List<Cheque> cheques = user.getCheques();
        BigDecimal totalSum = BigDecimal.ZERO;
        LocalDate now = LocalDate.now();
        if (date.isAfter(now)){
            throw new BedRequestException("cannot be in the future");
        }

        for (Cheque cheque : cheques) {
            LocalDate createdAt = cheque.getCreated();
            if (createdAt.equals(date)){
                totalSum = totalSum.add(cheque.getPriceAverage());
            }
        }
        return totalSum.divide(BigDecimal.valueOf(cheques.size()), RoundingMode.HALF_UP);
    }
}

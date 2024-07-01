package kursanov.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "cheques")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cheque {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cheque_seq")
    @SequenceGenerator(name = "cheque_seq", sequenceName = "cheque_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    public BigDecimal priceAverage;
    private LocalDate created;


    @PrePersist
    public void prePersist() {
       this.created = LocalDate.now();
    }


    @ManyToOne(cascade = CascadeType.DETACH)
    private User user;

    @ManyToMany(mappedBy = "cheques", cascade = {CascadeType.DETACH, CascadeType.MERGE})
    private List<Menu> menus;

    public void addMenu(Menu menu) {
        if (this.menus == null) this.menus = new ArrayList<>();
        this.menus.add(menu);
    }
}

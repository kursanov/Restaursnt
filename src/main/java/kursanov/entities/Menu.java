package kursanov.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menues")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_seq")
    @SequenceGenerator(name = "menu_seq", sequenceName = "menu_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String image;

    private BigDecimal price;

    private String description;

    private boolean isVegetarian;


    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    private List<Cheque> cheques;

    @ManyToOne(cascade = {CascadeType.DETACH})
    private Restaurant restaurant;

    @OneToOne(mappedBy = "menu", cascade = {CascadeType.REMOVE})
    private StopList stopList;

    @ManyToOne(cascade = {CascadeType.DETACH})
    private SubCategory subcategory;


    public void addCheque(Cheque cheque) {
        if (this.cheques == null) this.cheques = new ArrayList<>();
        this.cheques.add(cheque);
    }
}

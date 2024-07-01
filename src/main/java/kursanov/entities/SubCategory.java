package kursanov.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "subcategories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subcategory_seq")
    @SequenceGenerator(name = "subcategory_seq", sequenceName = "subcategory_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;


    @OneToMany(mappedBy = "subcategory", cascade = {CascadeType.REMOVE})
    private List<Menu> menus;


    @ManyToOne(cascade = {CascadeType.DETACH})
    private Category category;


    public void addMenu(Menu menu) {
        if (this.menus == null) this.menus = new ArrayList<>();
        this.menus.add(menu);
    }
}

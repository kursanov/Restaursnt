package kursanov.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @SequenceGenerator(name = "category_seq", sequenceName = "category_sequence", allocationSize = 1)
    private Long id;


    @Column(nullable = false)
    private String name;


    @OneToMany(mappedBy = "category", cascade = {CascadeType.REMOVE})
    private List<SubCategory> subCategories;


    @ManyToOne(cascade = {CascadeType.DETACH})
    private Restaurant restaurant;

    public void addSubCategory(SubCategory subCategory) {
        if (this.subCategories == null) this.subCategories = new ArrayList<>();
        this.subCategories.add(subCategory);
    }
}

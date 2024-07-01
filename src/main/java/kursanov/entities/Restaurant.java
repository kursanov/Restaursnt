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
@Table(name = "restaurants")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_seq")
    @SequenceGenerator(name = "restaurant_seq", sequenceName = "restaurant_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String resType;

    private int numberOfEmployees;

    private int service;

    @OneToMany(mappedBy = "restaurant", cascade = {CascadeType.REMOVE, CascadeType.REFRESH},fetch = FetchType.LAZY)
    private List<User> users;

    @OneToMany(mappedBy = "restaurant", cascade = {CascadeType.MERGE, CascadeType.REMOVE} )
    private List<Menu> menus;

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "restaurant")
    private List<Application> applications;

    @OneToMany(mappedBy = "restaurant", cascade = {CascadeType.REMOVE})
    private List<Category> categories;

    public void addUser(User user) {
        if (this.users == null) this.users = new ArrayList<>();
        this.users.add(user);
    }

    public void addApplication(Application application) {
        if (this.applications == null) this.applications = new ArrayList<>();
        this.applications.add(application);
    }

    public void addCategory(Category category) {
        if (this.categories == null) this.categories = new ArrayList<>();
        this.categories.add(category);
    }

    public void addMenu(Menu menu) {
        if (this.menus == null) this.menus = new ArrayList<>();
        this.menus.add(menu);
    }
}

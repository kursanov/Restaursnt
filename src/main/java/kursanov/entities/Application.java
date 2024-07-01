package kursanov.entities;

import jakarta.persistence.*;
import kursanov.enums.Role;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "applications")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_seq")
    @SequenceGenerator(name = "app_seq", sequenceName = "app_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String email;

    @Column(nullable = false)
    private String password;

    private String phoneNumber;


    @Enumerated(EnumType.STRING)
    private Role role;

    private int experience;

    @ManyToOne(cascade = {CascadeType.DETACH})
    private Restaurant restaurant;
}

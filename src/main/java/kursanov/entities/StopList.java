package kursanov.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "stopLists")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StopList {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stopList_seq")
    @SequenceGenerator(name = "stopList_seq", sequenceName = "stopList_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String reason;

    private LocalDate date;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    private Menu menu;

}

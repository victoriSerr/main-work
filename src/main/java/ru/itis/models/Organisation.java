package ru.itis.models;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private Long moderator_id;
    private String name;
    private String phoneNumber;
    private String address;
    private String description;
//
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "organisation")
    private List<Animal> animals;

    @ManyToOne
    @JoinColumn(name = "moderator_id")
    private AppUser moderator;

    @Override
    public String toString() {
        return "Organisation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", animals=" + animals +
                ", moderator=" + moderator +
                '}';
    }
}

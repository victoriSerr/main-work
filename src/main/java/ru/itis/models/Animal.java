package ru.itis.models;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table
public class Animal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private Long organisation_id;
    private String name;
    private String description;


    @Enumerated(EnumType.STRING)
    private AnimalStatus status;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "animalA")
    private List<FileInfo> files;

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", organisation=" + organisation.getName() +
                '}';
    }
}

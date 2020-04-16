package ru.itis.models;


import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private Long organisation_id;
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

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

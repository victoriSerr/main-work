package ru.itis.models;


import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table
public class Organisation implements Serializable {

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "organisation")
    @Where(clause = "status = 'IN_HOME'")
    private List<Animal> animalsInHome;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "organisation")
    @Where(clause = "status = 'HOMELESS'")
    private List<Animal> animalsNeedHome;

    @ManyToOne
    @JoinColumn(name = "moderator_id")
    private AppUser moderator;

    @OneToMany(mappedBy = "organisation")
    private List<Post> posts;

    @Override
    public String toString() {
        return "Organisation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", animals=" + animals +
                '}';
    }
}

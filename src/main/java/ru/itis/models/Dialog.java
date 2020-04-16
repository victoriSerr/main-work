package ru.itis.models;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table
public class Dialog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_from")
    private AppUser userFrom;

    @ManyToOne
    @JoinColumn(name = "user_to")
    private AppUser userTo;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dialog")
    private List<Message> messages;

    @Override
    public String toString() {
        return "Dialog{" +
                "id=" + id +
                ", userFrom=" + userFrom.getLogin() +
                ", userTo=" + userTo.getLogin() +
                ", messages=" + messages +
                '}';
    }
}

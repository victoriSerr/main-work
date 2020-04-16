package ru.itis.models;

import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.*;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String hashPassword;
    private String email;
    private String link;
    private Boolean isConfirmed;


    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "moderator", cascade = CascadeType.ALL)
    private Set<Organisation> organisation;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userTo", cascade = CascadeType.ALL)
    private List<Dialog> dialogs;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userFrom", cascade = CascadeType.ALL)
//    private List<Dialog> dialogs1;

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", hashPassword='" + hashPassword + '\'' +
                ", email='" + email + '\'' +
                ", link='" + link + '\'' +
                ", isConfirmed=" + isConfirmed +
                ", role=" + role +
                ", organisation=" + organisation +
                ", dialogs=" + dialogs +
//                ", dialogs1=" + dialogs1 +
                '}';
    }


//    @Transient
//    @Builder.Default
//    private Map<String, List<Message>> messages = new HashMap<>();



}


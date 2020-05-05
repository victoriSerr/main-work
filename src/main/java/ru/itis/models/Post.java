package ru.itis.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String text;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;
}

package ru.itis.models;


import lombok.*;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
@NoArgsConstructor
@Entity
@Table
public class FileInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String realName;
    private String nameInStorage;
    private Long size;
    private String suffix;
    private String storageUrl;

    @Transient
    private File sourсeFile;

    @ManyToOne(targetEntity = Animal.class)
    @JoinColumn(name = "animal_id")
    private Animal animalA;

    @PostLoad
    public void loadFile() {
        sourсeFile = new File(storageUrl);
    }
}

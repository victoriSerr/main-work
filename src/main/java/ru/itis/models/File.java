package ru.itis.models;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
@NoArgsConstructor
@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String realName;
    private String nameInStorage;
    private Long size;
    private String suffix;
    private String storageUrl;
}

package ru.itis.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PostDto {
    private String orgId;
    private String title;
    private String text;
}

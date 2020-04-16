package ru.itis.dto;


import lombok.*;
import ru.itis.models.AppUser;
import ru.itis.models.Message;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class MessageDto {

    private String dialogId;
    private String text;

//    public static MessageDto from(Message message) {
//        return MessageDto.builder()
//                .dialogId(message.getDialog().getId())
//                .text(message.getText())
//                .build();
//    }
}

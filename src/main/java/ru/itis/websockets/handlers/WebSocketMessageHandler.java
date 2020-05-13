package ru.itis.websockets.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.itis.dto.MessageDto;
import ru.itis.services.MessageService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class WebSocketMessageHandler extends TextWebSocketHandler {

    private static final Map<String, WebSocketSession> sessions = new HashMap<>();
    private static final Map<String, Map<String, WebSocketSession>> rooms = new HashMap<>();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageService messageService;

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        String messageText = (String) message.getPayload();
        MessageDto messageFromJson = objectMapper.readValue(messageText, MessageDto.class);

        String dialogId = messageFromJson.getDialogId();
        Map<String, WebSocketSession> map = new HashMap<>();

        if (rooms.containsKey(dialogId)) {
            map = rooms.get(dialogId);
        }

        map.put(messageFromJson.getLogin(), session);
        rooms.put(dialogId, map);

        for (WebSocketSession currentSession : map.values()) {
            if(!messageFromJson.getText().equals("")) {
                currentSession.sendMessage(message);
            }
        }
        if(!messageFromJson.getText().equals("")) {
            messageService.save(messageFromJson);
        }
    }

    public Set<String> getRoomsId() {
        return rooms.keySet();
    }
}

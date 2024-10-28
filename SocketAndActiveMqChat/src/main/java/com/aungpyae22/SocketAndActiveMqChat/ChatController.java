package com.aungpyae22.SocketAndActiveMqChat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final JmsTemplate jmsTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    @MessageMapping("/chat")
    @SendTo("/queue/messages")
    public void chatting(Message message){
        try {
            String messageJson = objectMapper.writeValueAsString(message);
            jmsTemplate.convertAndSend("demo",messageJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}

package com.aungpyae22.SocketAndActiveMqChat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JmsService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    @JmsListener(destination = "demo")
    public void messageListener(String messageJson){
        try {
            Message message = objectMapper.readValue(messageJson,Message.class);
            simpMessagingTemplate.convertAndSend("/queue/messages",message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

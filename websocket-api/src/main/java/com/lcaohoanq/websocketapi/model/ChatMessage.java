package com.lcaohoanq.websocketapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String from;
    private String content;
    private MessageType type;
    
    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
}

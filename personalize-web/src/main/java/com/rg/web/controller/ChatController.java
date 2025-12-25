package com.rg.web.controller;

import com.rg.web.dto.ChatRequest;
import com.rg.web.dto.ChatResponse;
import com.rg.web.service.AiChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:4200")
public class ChatController {

    @Autowired
    private AiChatService aiChatService;

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        try {
            String response = aiChatService.getChatResponse(request.getMessage(), request.getConversationId());
            return ResponseEntity.ok(new ChatResponse(response));
        } catch (Exception e) {
            return ResponseEntity.ok(new ChatResponse(
                "Sorry, I encountered an error. Please try again or contact support at support@personalizeu.com"
            ));
        }
    }
}

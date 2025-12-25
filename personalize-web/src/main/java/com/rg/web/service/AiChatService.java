package com.rg.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AiChatService {

    @Value("${ai.provider:fallback}")
    private String aiProvider;

    @Value("${ai.openai.api-key:}")
    private String openaiApiKey;

    @Value("${ai.openai.model:gpt-3.5-turbo}")
    private String openaiModel;

    @Value("${ai.gemini.api-key:}")
    private String geminiApiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final Map<String, List<Map<String, String>>> conversations = new HashMap<>();

    public String getChatResponse(String userMessage, String conversationId) {
        switch (aiProvider.toLowerCase()) {
            case "openai":
                return getOpenAiResponse(userMessage, conversationId);
            case "gemini":
                return getGeminiResponse(userMessage);
            case "fallback":
            default:
                return getFallbackResponse(userMessage);
        }
    }

    private String getOpenAiResponse(String userMessage, String conversationId) {
        if (openaiApiKey == null || openaiApiKey.isEmpty()) {
            return getFallbackResponse(userMessage);
        }

        try {
            List<Map<String, String>> messages = conversations.computeIfAbsent(conversationId, k -> {
                List<Map<String, String>> list = new ArrayList<>();
                Map<String, String> systemMsg = new HashMap<>();
                systemMsg.put("role", "system");
                systemMsg.put("content", "You are a helpful customer support assistant for Personalize U. Be friendly, concise, and helpful.");
                list.add(systemMsg);
                return list;
            });

            Map<String, String> userMsg = new HashMap<>();
            userMsg.put("role", "user");
            userMsg.put("content", userMessage);
            messages.add(userMsg);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(openaiApiKey);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", openaiModel);
            requestBody.put("messages", messages);
            requestBody.put("max_tokens", 150);
            requestBody.put("temperature", 0.7);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            
            ResponseEntity<Map> response = restTemplate.exchange(
                "https://api.openai.com/v1/chat/completions",
                HttpMethod.POST,
                entity,
                Map.class
            );

            if (response.getBody() != null) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    String aiResponse = (String) message.get("content");
                    
                    Map<String, String> assistantMsg = new HashMap<>();
                    assistantMsg.put("role", "assistant");
                    assistantMsg.put("content", aiResponse);
                    messages.add(assistantMsg);
                    
                    return aiResponse;
                }
            }
        } catch (Exception e) {
            System.err.println("OpenAI API Error: " + e.getMessage());
        }

        return getFallbackResponse(userMessage);
    }

    private String getGeminiResponse(String userMessage) {
        if (geminiApiKey == null || geminiApiKey.isEmpty()) {
            return getFallbackResponse(userMessage);
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = new HashMap<>();
            List<Map<String, Object>> contents = new ArrayList<>();
            Map<String, Object> content = new HashMap<>();
            List<Map<String, String>> parts = new ArrayList<>();
            Map<String, String> part = new HashMap<>();
            part.put("text", userMessage);
            parts.add(part);
            content.put("parts", parts);
            contents.add(content);
            requestBody.put("contents", contents);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            
            String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + geminiApiKey;
            
            ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                Map.class
            );

            if (response.getBody() != null) {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
                if (candidates != null && !candidates.isEmpty()) {
                    Map<String, Object> content1 = (Map<String, Object>) candidates.get(0).get("content");
                    List<Map<String, String>> parts1 = (List<Map<String, String>>) content1.get("parts");
                    return parts1.get(0).get("text");
                }
            }
        } catch (Exception e) {
            System.err.println("Gemini API Error: " + e.getMessage());
        }

        return getFallbackResponse(userMessage);
    }

    private String getFallbackResponse(String userMessage) {
        String msg = userMessage.toLowerCase();

        if (msg.contains("hello") || msg.contains("hi")) {
            return "Hi there! How can I assist you today?";
        } else if (msg.contains("help")) {
            return "I'm here to help! You can ask me about our features, account settings, or any issues you're experiencing.";
        } else if (msg.contains("feature") || msg.contains("what can")) {
            return "Our platform offers personalized dashboards, messaging, notifications, and more. You can customize your dashboard with tiles, manage your profile, and stay connected.";
        } else if (msg.contains("contact") || msg.contains("email") || msg.contains("phone")) {
            return "You can reach us at support@personalizeu.com or call us at +1-555-0123. We're available 24/7!";
        } else if (msg.contains("price") || msg.contains("cost")) {
            return "We offer flexible pricing plans. Would you like to learn more about our plans?";
        } else if (msg.contains("thank")) {
            return "You're welcome! Is there anything else I can help you with?";
        } else {
            return "Thank you for your message. I'm here to help with questions about Personalize U. Could you provide more details?";
        }
    }

    public void clearConversation(String conversationId) {
        conversations.remove(conversationId);
    }
}

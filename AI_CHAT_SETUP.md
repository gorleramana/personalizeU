# AI Chat Configuration

## How to Enable AI in Your Chat

You have been provided with **4 options** for AI integration:

### 1. **OpenAI ChatGPT** (Recommended for best responses)
- Get API key from: https://platform.openai.com/api-keys
- Add to `application.properties`:
  ```properties
  ai.provider=openai
  ai.openai.api-key=your-api-key-here
  ai.openai.model=gpt-3.5-turbo
  ```
- In frontend `ai-chat.service.ts`, uncomment:
  ```typescript
  return this.sendToOpenAI(userMessage);
  ```

### 2. **Google Gemini** (Free tier available)
- Get API key from: https://makersuite.google.com/app/apikey
- Add to `application.properties`:
  ```properties
  ai.provider=gemini
  ai.gemini.api-key=your-api-key-here
  ```
- In frontend `ai-chat.service.ts`, uncomment:
  ```typescript
  return this.sendToGemini(userMessage);
  ```

### 3. **Backend Proxy** (RECOMMENDED - keeps API keys secure)
- Add API keys to backend `application.properties`:
  ```properties
  ai.provider=openai
  ai.openai.api-key=your-api-key-here
  ai.openai.model=gpt-3.5-turbo
  ```
- Update SecurityConfiguration to permit `/api/chat/**`
- In frontend `ai-chat.service.ts`, uncomment:
  ```typescript
  return this.sendToBackend(userMessage);
  ```

### 4. **Fallback (No API required)** - Current default
- Simple rule-based responses
- No configuration needed
- Already active

## Security Best Practices

⚠️ **NEVER commit API keys to version control!**

### For Production:
1. Use environment variables
2. Store API keys on backend only
3. Use backend proxy (Option 3)
4. Add rate limiting
5. Implement authentication

### Backend Configuration:
Add to `application.properties`:
```properties
# Choose provider: openai, gemini, or fallback
ai.provider=fallback

# OpenAI Configuration
ai.openai.api-key=${OPENAI_API_KEY:}
ai.openai.model=gpt-3.5-turbo

# Google Gemini Configuration
ai.gemini.api-key=${GEMINI_API_KEY:}
```

Set environment variables:
```bash
export OPENAI_API_KEY=your-key-here
# or
export GEMINI_API_KEY=your-key-here
```

## Cost Considerations

### OpenAI Pricing (as of 2024):
- GPT-3.5-turbo: ~$0.001 per 1K tokens
- GPT-4: ~$0.03 per 1K tokens

### Google Gemini:
- Free tier: 60 requests/minute
- Paid tier available

## Features Implemented

✅ Conversation history tracking
✅ Typing indicators
✅ Auto-scroll to latest message
✅ Error handling
✅ Multiple AI provider support
✅ Fallback responses
✅ Session-based conversation IDs

## Files Created/Modified

**Frontend:**
- `ai-chat.service.ts` - AI integration service
- `chat-window.component.ts` - Updated to use AI service
- `chat-window.component.html` - Added typing indicator
- `chat-window.component.css` - Typing animation
- `environment.development.ts` - Configuration

**Backend:**
- `ChatController.java` - REST endpoint
- `AiChatService.java` - AI provider integration
- `ChatRequest.java` - DTO
- `ChatResponse.java` - DTO

## Testing

1. Run backend: `mvn spring-boot:run`
2. Run frontend: `ng serve`
3. Click "Contact Us" in footer
4. Type a message and test the response

## Next Steps

1. Choose your AI provider
2. Get API key
3. Configure application.properties
4. Update SecurityConfiguration to permit `/api/chat`
5. Update `sendMessage()` method in `ai-chat.service.ts`
6. Test the integration

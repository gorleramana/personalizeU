import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

export interface ChatMessage {
  role: 'user' | 'assistant' | 'system';
  content: string;
}

@Injectable({
  providedIn: 'root'
})
export class AiChatService {
  // Choose your AI provider:
  // 1. OpenAI (ChatGPT)
  // 2. Google Gemini
  // 3. Anthropic Claude
  // 4. Your own backend endpoint
  
  private readonly OPENAI_API_URL = 'https://api.openai.com/v1/chat/completions';
  private readonly GEMINI_API_URL = 'https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent';
  
  // IMPORTANT: In production, never store API keys in frontend code!
  // Use environment variables and proxy through your backend
  private readonly API_KEY = ''; // Add your API key here or use environment variables
  
  private conversationHistory: ChatMessage[] = [
    {
      role: 'system',
      content: 'You are a helpful customer support assistant for Personalize U, a personalized dashboard application. Be friendly, concise, and helpful. Help users with features, account issues, and general questions.'
    }
  ];

  constructor(private http: HttpClient) {}

  /**
   * Send message to OpenAI ChatGPT
   */
  sendToOpenAI(userMessage: string): Observable<string> {
    this.conversationHistory.push({
      role: 'user',
      content: userMessage
    });

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.API_KEY}`
    });

    const body = {
      model: 'gpt-3.5-turbo', // or 'gpt-4' for better responses
      messages: this.conversationHistory,
      max_tokens: 150,
      temperature: 0.7
    };

    return this.http.post<any>(this.OPENAI_API_URL, body, { headers }).pipe(
      map(response => {
        const aiResponse = response.choices[0].message.content;
        this.conversationHistory.push({
          role: 'assistant',
          content: aiResponse
        });
        return aiResponse;
      }),
      catchError(error => {
        console.error('OpenAI API Error:', error);
        return of('Sorry, I encountered an error. Please try again or contact support at support@personalizeu.com');
      })
    );
  }

  /**
   * Send message to Google Gemini
   */
  sendToGemini(userMessage: string): Observable<string> {
    const url = `${this.GEMINI_API_URL}?key=${this.API_KEY}`;
    
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    const body = {
      contents: [{
        parts: [{
          text: userMessage
        }]
      }]
    };

    return this.http.post<any>(url, body, { headers }).pipe(
      map(response => {
        return response.candidates[0].content.parts[0].text;
      }),
      catchError(error => {
        console.error('Gemini API Error:', error);
        return of('Sorry, I encountered an error. Please try again or contact support at support@personalizeu.com');
      })
    );
  }

  /**
   * Send message to your backend API (RECOMMENDED for production)
   * This keeps your API keys secure on the server side
   */
  sendToBackend(userMessage: string): Observable<string> {
    const url = '/api/chat'; // Your backend endpoint
    
    const body = {
      message: userMessage,
      conversationId: this.getConversationId()
    };

    return this.http.post<any>(url, body).pipe(
      map(response => response.message),
      catchError(error => {
        console.error('Backend API Error:', error);
        return of('Sorry, I encountered an error. Please try again or contact support at support@personalizeu.com');
      })
    );
  }

  /**
   * Fallback to rule-based responses (no AI required)
   */
  getFallbackResponse(userMessage: string): Observable<string> {
    const msg = userMessage.toLowerCase();
    let response = '';

    if (msg.includes('hello') || msg.includes('hi')) {
      response = 'Hi there! How can I assist you today?';
    } else if (msg.includes('help')) {
      response = 'I\'m here to help! You can ask me about our features, account settings, or any issues you\'re experiencing.';
    } else if (msg.includes('feature') || msg.includes('what can')) {
      response = 'Our platform offers personalized dashboards, messaging, notifications, and more. You can customize your dashboard with tiles, manage your profile, and stay connected with real-time updates.';
    } else if (msg.includes('contact') || msg.includes('email') || msg.includes('phone')) {
      response = 'You can reach us at support@personalizeu.com or call us at +1-555-0123. We\'re available 24/7!';
    } else if (msg.includes('price') || msg.includes('cost') || msg.includes('plan')) {
      response = 'We offer flexible pricing plans starting at $9.99/month. Would you like to learn more about our plans?';
    } else if (msg.includes('thank')) {
      response = 'You\'re welcome! Is there anything else I can help you with?';
    } else {
      response = 'Thank you for your message. I\'m here to help with questions about Personalize U. Could you provide more details about what you\'re looking for?';
    }

    return of(response);
  }

  /**
   * Main method to send message - choose your implementation
   */
  sendMessage(userMessage: string): Observable<string> {
    // Choose one of the following:
    
    // Option 1: Use OpenAI (requires API key)
    // return this.sendToOpenAI(userMessage);
    
    // Option 2: Use Google Gemini (requires API key)
    // return this.sendToGemini(userMessage);
    
    // Option 3: Use your backend (RECOMMENDED)
    // return this.sendToBackend(userMessage);
    
    // Option 4: Fallback to rule-based (no API required)
    return this.getFallbackResponse(userMessage);
  }

  clearHistory(): void {
    this.conversationHistory = this.conversationHistory.slice(0, 1); // Keep system message
  }

  private getConversationId(): string {
    // Generate or retrieve conversation ID for tracking
    let id = sessionStorage.getItem('chat_conversation_id');
    if (!id) {
      id = 'conv_' + Date.now() + '_' + Math.random().toString(36).substr(2, 9);
      sessionStorage.setItem('chat_conversation_id', id);
    }
    return id;
  }
}

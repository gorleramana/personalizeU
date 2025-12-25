import { Component, EventEmitter, Output } from '@angular/core';
import { AiChatService } from '../../services/ai-chat.service';

@Component({
  selector: 'app-chat-window',
  templateUrl: './chat-window.component.html',
  styleUrls: ['./chat-window.component.css']
})
export class ChatWindowComponent {
  @Output() close = new EventEmitter<void>();
  
  messages: { text: string; sender: 'user' | 'bot'; timestamp: Date }[] = [
    { 
      text: 'Hello! Welcome to Personalize U. How can we help you today?', 
      sender: 'bot', 
      timestamp: new Date() 
    }
  ];
  
  newMessage = '';
  isMinimized = false;
  isTyping = false;

  constructor(private aiChatService: AiChatService) {}

  sendMessage(): void {
    if (this.newMessage.trim()) {
      const userMessage = this.newMessage;
      
      // Add user message to chat
      this.messages.push({
        text: userMessage,
        sender: 'user',
        timestamp: new Date()
      });
      
      this.newMessage = '';
      this.isTyping = true;
      
      // Get AI response
      this.aiChatService.sendMessage(userMessage).subscribe({
        next: (response) => {
          this.isTyping = false;
          this.messages.push({
            text: response,
            sender: 'bot',
            timestamp: new Date()
          });
          
          // Auto-scroll to bottom
          setTimeout(() => this.scrollToBottom(), 100);
        },
        error: (error) => {
          this.isTyping = false;
          console.error('Chat error:', error);
          this.messages.push({
            text: 'Sorry, I encountered an error. Please try again or contact support at support@personalizeu.com',
            sender: 'bot',
            timestamp: new Date()
          });
        }
      });
      
      // Auto-scroll to bottom
      setTimeout(() => this.scrollToBottom(), 100);
    }
  }
  
  private scrollToBottom(): void {
    const container = document.querySelector('.messages-container');
    if (container) {
      container.scrollTop = container.scrollHeight;
    }
  }

  toggleMinimize(): void {
    this.isMinimized = !this.isMinimized;
  }

  closeChat(): void {
    this.close.emit();
  }
}

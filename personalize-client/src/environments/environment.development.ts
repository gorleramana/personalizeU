export const environment = {
  production: false,
  // AI Configuration
  ai: {
    provider: 'fallback', // 'openai', 'gemini', 'backend', or 'fallback'
    openai: {
      apiKey: '', // Add your OpenAI API key here (or use backend proxy)
      model: 'gpt-3.5-turbo'
    },
    gemini: {
      apiKey: '', // Add your Gemini API key here
      model: 'gemini-pro'
    },
    backend: {
      endpoint: 'http://localhost:8085/api/chat'
    }
  }
};

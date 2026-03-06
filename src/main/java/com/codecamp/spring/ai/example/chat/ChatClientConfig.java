package com.codecamp.spring.ai.example.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {

        SimpleLoggerAdvisor logger = new SimpleLoggerAdvisor();

        /*
        - ChatClientConfig registers MessageChatMemoryAdvisor for storing chat messages
        - MessageChatMemoryAdvisor triggers MessageWindowChatMemory
        - MessageWindowChatMemory triggers ChatMemoryRepository
        - the previews stored user and assistant messages are attached to the current prompt
        ---
        - MessageWindowChatMemory uses by default an InMemoryChatMemoryRepository,
          so the messages will be stored in a ConcurrentHashMap,
          but you can also use a persistent database (e.g. Postgres)
        ---
        - if no CONVERSATION_ID is provided to the ChatClient, the ChatMemory uses a default one
        - to distinguish between different conversations, add a unique id
        */
        MessageChatMemoryAdvisor memory = MessageChatMemoryAdvisor.builder(chatMemory).build();

        return chatClientBuilder
                .defaultAdvisors(logger, memory)
                .build();
    }
}

package com.codecamp.spring.ai.example.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, VectorStore vectorStore) {

        SimpleLoggerAdvisor logger = new SimpleLoggerAdvisor();

        /*
        - ChatClientConfig registers MessageChatMemoryAdvisor for storing chat messages
        - MessageChatMemoryAdvisor triggers MessageWindowChatMemory
        - MessageWindowChatMemory triggers ChatMemoryRepository
        - the previews stored user and assistant messages are attached to the current prompt
        ---
        - MessageWindowChatMemory uses by default an InMemoryChatMemoryRepository,
          so the messages will be stored in a ConcurrentHashMap,
          but you can also use a persistent database (e.g. via JdbcChatMemoryRepository)
        ---
        - if no CONVERSATION_ID is provided to the ChatClient, the ChatMemory uses a default one
        - to distinguish between different conversations, add a unique id
        */
        MessageChatMemoryAdvisor memory = MessageChatMemoryAdvisor.builder(chatMemory).build();

        /*
        - VectorStoreConfig creates a SimpleVectorStore with file persistence and loads it with sample documents
        - QuestionAnswerAdvisor uses the vector store to find relevant documents based on the user query and adds them as context in the prompt
        - you can modify the search behavior by providing a custom SearchRequest
         */
        QuestionAnswerAdvisor rag = QuestionAnswerAdvisor.builder(vectorStore)
                .searchRequest(SearchRequest.builder().similarityThreshold(0.5).build()) // 0.0 -> 0.5
                .build();

        return chatClientBuilder
                .defaultAdvisors(logger, memory, rag)
                .build();
    }
}

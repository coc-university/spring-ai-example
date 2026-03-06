package com.codecamp.spring.ai.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

@Configuration
public class VectorStoreConfig {

    private static final Logger log = LoggerFactory.getLogger(VectorStoreConfig.class);
    private static final String VECTOR_STORE_NAME = "vectorstore.json";

    @Bean
    SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel) {

        SimpleVectorStore vectorStore = SimpleVectorStore.builder(embeddingModel).build();
        File vectorStoreFile = getVectorStoreFile();

        if (vectorStoreFile.exists()) {
            vectorStore.load(vectorStoreFile);
            log.info("Loaded vector store file: {}", vectorStoreFile.getAbsolutePath());
        } else {
            vectorStore.add(loadDocuments());
            vectorStore.save(vectorStoreFile);
            log.info("Created new vector store file: {}", vectorStoreFile.getAbsolutePath());
        }

        return vectorStore;
    }

    private File getVectorStoreFile() {
        return Paths.get("src", "main", "resources", "data", VECTOR_STORE_NAME).toFile();
    }

    private List<Document> loadDocuments() {
        return List.of(
                new Document("name: Robin, rolle: Backend Entwickler, technologien: Java & Spring"),
                new Document("name: Daniel, rolle: Fullstack Entwickler, technologien: React & Spring"),
                new Document("name: Laura, rolle: Frontend Entwicklerin, technologien: Vue.js & TypeScript"),
                new Document("name: Markus, rolle: DevOps Engineer, technologien: Kubernetes & Terraform"),
                new Document("name: Sarah, rolle: Data Scientist, technologien: Python & TensorFlow"),
                new Document("name: Jonas, rolle: Mobile Entwickler, technologien: Flutter & Dart"),
                new Document("name: Anna, rolle: Backend Entwicklerin, technologien: Node.js & PostgreSQL"),
                new Document("name: Felix, rolle: Cloud Architect, technologien: AWS & Docker"),
                new Document("name: Mia, rolle: QA Engineer, technologien: Selenium & Cypress"),
                new Document("name: Lukas, rolle: Fullstack Entwickler, technologien: Angular & MongoDB")
        );
    }
}


# Spring AI Example

## Important Setup

- add your OPENAI_API_KEY to the environment (e.g. in Intellij), check out application.yml
- no database needed, the vector store and chat memory are in-memory, just run the app

## Chat Memory

- Info:
  - https://docs.spring.io/spring-ai/reference/api/chat-memory.html
  - store previous conversations for better context in future interactions
- Setup: 
  - MessageChatMemoryAdvisor (check out ChatClientConfig)
  - can be in-memory or persistent
- Testing:
  - add breakpoint in MessageChatMemoryAdvisor-before() to see stored messages
  - Mein Name ist Robin → http://localhost:8080/chat?message=Mein%20Name%20ist%20Robin
  - Wie ist mein Name? → http://localhost:8080/chat?message=Wie%20ist%20mein%20Name%3F
  - the previews stored user and assistant messages are attached to the current prompt 
  - so the assistant has context and can answer the question correctly
- Other examples:
  - https://github.com/danvega/spring-ai-workshop/blob/main/src/main/java/dev/danvega/workshop/memory/StatefulController.java
  - https://github.com/joshlong-attic/2026-02-18-bootiful-dogumentary/blob/main/assistant/src/main/java/com/example/assistant/AssistantApplication.java

## RAG

- Info:
  - https://docs.spring.io/spring-ai/reference/api/retrieval-augmented-generation.html
  - query in vector database to find additional documents before calling the ai
- Setup: 
  - for easy use-cases: QuestionAnswerAdvisor + SearchRequest (check out ChatClientConfig)
  - for more complex use-cases: RetrievalAugmentationAdvisor
  - the vector store can be an in-memory file or a persistent database
- Testing:
  - add breakpoint in QuestionAnswerAdvisor-before() to see similar documents
  - Womit kennt sich Robin aus?→ http://localhost:8080/chat?message=Womit%20kennt%20sich%20Robin%20aus%3F
    - vectorStore.similaritySearch() finds 4 documents (because of DEFAULT_TOP_K = 4)
    - the ai filters out Robin and answers correct
  - Welche Backend-Entwickler kennst du? → http://localhost:8080/chat?message=Welche%20Backend-Entwickler%20kennst%20du%3F
    - vectorStore.similaritySearch() finds apart from 2 backend-dev's also 2 fullstack-dev's (because similar skill-set)
    - the ai filters out the backend-dev's and answers correct
  - Wer kennt sich mit Datenhaltung aus? → http://localhost:8080/chat?message=Wer%20kennt%20sich%20mit%20Datenhaltung%20aus%3F
    - vectorStore.similaritySearch() finds apart from Postgres & MongoDB-experts also 2 Spring-dev's
    - the ai filters out the db-experts and answers correct
- Other examples:
  - https://github.com/danvega/spring-ai-workshop/blob/main/src/main/java/dev/danvega/workshop/rag/ModelsController.java
  - https://github.com/joshlong-attic/2026-02-18-bootiful-dogumentary/blob/main/assistant/src/main/java/com/example/assistant/AssistantApplication.java
  - do RAG manually, check of https://github.com/coc-university/spring-ai-rag-example

## Tool Calling

- Info:
  - https://docs.spring.io/spring-ai/reference/api/tool-calling.html
  - the ai requests a call to a specific tool to get additional information about a topic
- Setup:
  - for internal tools: use @Tool on method in spring bean (check out DemoRepoTools)
  - for external tools that are on a remote server use mcp (not in this repo)
    - info: https://docs.spring.io/spring-ai/reference/1.0/api/mcp/mcp-overview.html
    - example: https://github.com/coc-university/spring-ai-mcp-example
- Testing
  - Erzähle mir etwas über Robin und seine Demo-Repos → http://localhost:8080/chat?message=Erz%C3%A4hle%20mir%20etwas%20%C3%BCber%20Robin%20und%20seine%20Demo-Repos
    - the ai calls the tool getDemoRepoFromOwner() to get the information about Robin's repos and answers correct
- Other examples:
  - https://github.com/danvega/spring-ai-workshop/tree/main/src/main/java/dev/danvega/workshop/tools
  - https://github.com/tzolov/playground-flight-booking/blob/main/src/main/java/ai/spring/demo/ai/playground/services/BookingTools.java

## Architecture

![Arc](docs/Arc.drawio.png)


# Spring AI Example

## Chat Memory

- Setup: MessageChatMemoryAdvisor in ChatClientConfig
- Testing:
  - Mein Name ist Robin → http://localhost:8080/chat?message=Mein%20Name%20ist%20Robin
  - Wie ist mein Name? → http://localhost:8080/chat?message=Wie%20ist%20mein%20Name%3F
  - the previews stored user and assistant messages are attached to the current prompt 
  - so the assistant has context and can answer the question correctly

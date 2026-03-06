package com.codecamp.spring.ai.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {

        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor()) // set log-level in app.yaml to see the advisor in action
                .build();
    }

    @GetMapping("/chat")
    public String docs(@RequestParam(defaultValue = "Welche Dokumente hat Robin?")  String message) {
        String response = chatClient.prompt()
                .user(message)
                .call()
                .content();
        log.info("\n\nChat response: \n{}\n", response);
        return response;
    }

}

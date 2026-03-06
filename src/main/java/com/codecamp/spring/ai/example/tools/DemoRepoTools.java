package com.codecamp.spring.ai.example.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DemoRepoTools {

    private static final Logger log = LoggerFactory.getLogger(DemoRepoTools.class);

    // in a real application, you would probably fetch the items from a database or an external service
    private final List<DemoRepo> demos = List.of(
            new DemoRepo("Robin", "spring-ddd-example", "An example for domain-driven spring app"),
            new DemoRepo("Peter", "python-example", "An example of a Python project")
    );

    @Tool(name = "get-all-demo-repos", description = "Get a list of all demo repositories")
    public List<DemoRepo> getAllDemoRepos() {
        log.info("tool-call: return all demo repos");
        return demos;
    }

    @Tool(name = "get-demo-repo-by-name", description = "Get a single demo repository by name")
    public DemoRepo getDemoRepoByName(String name) {
        log.info("tool-call: return demo repo with name: {}", name);
        return demos
                .stream()
                .filter(demo -> demo.name().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Tool(name = "get-demo-repo-from-owner", description = "Get a single demo repository from owner")
    public DemoRepo getDemoRepoFromOwner(String owner) {
        log.info("tool-call: return demo repo from owner: {}", owner);
        return demos
                .stream()
                .filter(demo -> demo.owner().equals(owner))
                .findFirst()
                .orElse(null);
    }
}

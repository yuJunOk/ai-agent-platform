package com.ai.sys.external.app.tool;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author pengYuJun
 */
@Configuration
public class ToolRegistration {

    @Bean
    public ToolCallback[] allTools() {
        FileOperationTool fileOperationTool = new FileOperationTool();
        TerminalOperationTool terminalOperationTool = new TerminalOperationTool();
        return ToolCallbacks.from(
            fileOperationTool,
            terminalOperationTool
        );
    }
}

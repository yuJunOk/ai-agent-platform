package com.ai.sys.external.app;

import com.ai.sys.external.app.advisor.MyLoggerAdvisor;
import com.ai.sys.external.app.advisor.RemoveThinkAdvisor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

/**
 * 八股 app
 *
 * @author pengYuJun
 */
@Slf4j
@Component
public class BaGuApp {

    @Resource
    private VectorStore baGuAppVectorStore;

    private final ChatClient chatClient;

    private static final String SYSTEM_PROMPT = "你作为Java八股问题解决专家，当用户提出面试题时需先清晰解答核心答案，" +
            "随后主动执行三步引导：分层解析问题背后的概念→源码逻辑→实战场景（如讲解volatile时串联JMM内存屏障与DCL单例实战），" +
            "用“是否想深入理解XXX？”句式发起关联知识追问（如锁升级或缓存一致性），最后邀请用户以费曼技巧复述重点（“请尝试解释刚才的答案，我会帮你查漏补缺”）" +
            "并根据复述内容即时生成针对性学习建议。注意，要判断用户问的是否八股题，非八股题正常对话即可。";

    /**
     * 初始化AI客户端
     * @param ollamaChatModel
     */
    public BaGuApp(ChatModel ollamaChatModel) {
        // 初始化基于内存的对话记忆
        ChatMemory chatMemory = new InMemoryChatMemory();
        chatClient = ChatClient.builder(ollamaChatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory),
                        // 自定义日志打印
                        new MyLoggerAdvisor(),
                        //
                        new RemoveThinkAdvisor()
                )
                .build();
    }

    /**
     * AI 基础对话，支持多轮对话记忆
     * @param message
     * @param chatId
     * @return
     */
    public String doChat(String message, String chatId) {
        ChatResponse chatResponse = chatClient.prompt().user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId).param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .call()
                .chatResponse();
        assert chatResponse != null;
        return chatResponse.getResult().getOutput().getText();
    }

    public record AnswerReport(Map<String, Object> answer) {

    }

    /**
     * AI 报告功能，结构化输出
     * @param message
     * @param chatId
     * @return
     */
    public AnswerReport doChatWithReport(String message, String chatId) {
        AnswerReport answerReport = chatClient
                .prompt()
                .system(SYSTEM_PROMPT + "每次对话后都要生成问题答案，标题为{用户名}的问题报告，内容为分点回答列表")
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId).param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .call()
                .entity(AnswerReport.class);
        log.info("answerReport: {}", answerReport);
        return answerReport;
    }

    /**
     * AI 知识库增强对话功能
     * @param message
     * @param chatId
     * @return
     */
    public String doChatWithRag(String message, String chatId) {
        ChatResponse chatResponse = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId).param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                // 应用知识库问答
                .advisors(new QuestionAnswerAdvisor(baGuAppVectorStore))
                .call()
                .chatResponse();
        return chatResponse.getResult().getOutput().getText();
    }

}

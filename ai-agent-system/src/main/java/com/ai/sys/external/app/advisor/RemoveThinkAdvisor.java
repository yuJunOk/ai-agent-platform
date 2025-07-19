package com.ai.sys.external.app.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.*;
import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.MessageAggregator; // 关键新增
import reactor.core.publisher.Flux;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

/**
 * 去掉think内容
 * @author pengYuJun
 */
@Slf4j
public class RemoveThinkAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {

    private static final Pattern THINK_PATTERN =
            Pattern.compile("<think>(.*?)</think>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 100;
    }

    @Override
    public AdvisedResponse aroundCall(AdvisedRequest request, CallAroundAdvisorChain chain) {
        AdvisedResponse response = chain.nextAroundCall(request);
        return filterThinkContent(response);
    }

    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest request, StreamAroundAdvisorChain chain) {
        MessageAggregator aggregator = new MessageAggregator();
        return aggregator.aggregateAdvisedResponse(
                chain.nextAroundStream(request),
                // 聚合后统一处理
                this::filterThinkContent
        );
    }

    private AdvisedResponse filterThinkContent(AdvisedResponse response) {
        if (response.response() == null || response.response().getResults().isEmpty()) {
            return response;
        }

        List<Generation> newGenerations = new ArrayList<>();
        for (Generation gen : response.response().getResults()) {
            String originalText = gen.getOutput().getText();
            // 彻底移除标签（包括内容）
            String filteredText = THINK_PATTERN.matcher(originalText).replaceAll("").trim();

            // 重建不可变对象链（正确做法）
            AssistantMessage newMessage = new AssistantMessage(filteredText, gen.getOutput().getMetadata());
            Generation newGen = new Generation(newMessage, gen.getMetadata());
            newGenerations.add(newGen);
        }

        ChatResponse newResponse = new ChatResponse(newGenerations, response.response().getMetadata());
        return new AdvisedResponse(newResponse, response.adviseContext());
    }
}
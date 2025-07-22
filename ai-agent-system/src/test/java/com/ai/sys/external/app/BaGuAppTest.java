package com.ai.sys.external.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class BaGuAppTest {

    @Resource
    private BaGuApp baGuApp;

    @Test
    void doChat() {
        String chatId = UUID.randomUUID().toString();
        // 第一轮
        String message = "你好，我是pengYuJun";
        String answer = baGuApp.doChat(message, chatId);
        // 第二轮
        message = "jdk和jre的区别是啥呢？";
        answer = baGuApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
        // 第三轮
        message = "我刚刚问的什么来着？";
        answer = baGuApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithReport() {
        String chatId = UUID.randomUUID().toString();
        String message = "你好，我是pengYuJun，我想知道jdk和jre的区别是啥呢？";
        BaGuApp.AnswerReport answer = baGuApp.doChatWithReport(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithRag() {
        String chatId = UUID.randomUUID().toString();
        String message = "你好，我是pengYuJun，我想知道MySQL 的索引类型有哪些？";
        String answer = baGuApp.doChatWithRag(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithTools() {
        String chatId = UUID.randomUUID().toString();
        String message = "你好，我是pengYuJun，我想知道MySQL 的索引类型有哪些？ 将回答保存为文件。";
        String answer = baGuApp.doChatWithTools(message, chatId);
        Assertions.assertNotNull(answer);
    }
}
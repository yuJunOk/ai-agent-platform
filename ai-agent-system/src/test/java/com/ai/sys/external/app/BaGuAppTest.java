package com.ai.sys.external.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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
}
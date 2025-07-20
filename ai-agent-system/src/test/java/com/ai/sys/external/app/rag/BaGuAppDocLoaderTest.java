package com.ai.sys.external.app.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BaGuAppDocLoaderTest {

    @Resource
    private BaGuAppDocLoader baGuAppDocLoader;

    @Test
    void loadMarkdowns() {
        baGuAppDocLoader.loadMarkdowns();
    }
}
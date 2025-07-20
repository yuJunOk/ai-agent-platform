package com.ai.sys.external.app.rag;

import jakarta.annotation.Resource;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 八股助手向量数据库配置
 * @author pengYuJun
 */
@Configuration
public class BaGuAppVectorStoreConfig {

    @Resource
    private BaGuAppDocLoader baGuAppDocLoader;

    @Bean
    VectorStore baGuAppVectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(embeddingModel).build();
        simpleVectorStore.add(baGuAppDocLoader.loadMarkdowns());
        return simpleVectorStore;
    }

}

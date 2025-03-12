package com.example.application.config;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.commons.marshall.JavaSerializationMarshaller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfinispanConfig {

    @Bean
    public RemoteCacheManager remoteCacheManager() {
        ConfigurationBuilder builder = new ConfigurationBuilder()
                .addServer()
                .host("localhost")
                .port(11222)
                .security()
                .authentication()
                .username("admin")
                .password("admin")
                .marshaller(new JavaSerializationMarshaller())
                .addJavaSerialAllowList("com.example.application.models.*");

        return new RemoteCacheManager(builder.build());
    }
}

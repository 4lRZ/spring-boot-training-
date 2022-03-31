package com.alrz.cursomc.config;

import com.alrz.cursomc.entities.PagamentoComBoletoEntity;
import com.alrz.cursomc.entities.PagamentoComCartaoEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
            public void configure(ObjectMapper objectMapper) {
                objectMapper.registerSubtypes(PagamentoComCartaoEntity.class);
                objectMapper.registerSubtypes(PagamentoComBoletoEntity.class);
                super.configure(objectMapper);
            }
        };
        return builder;
    }
}


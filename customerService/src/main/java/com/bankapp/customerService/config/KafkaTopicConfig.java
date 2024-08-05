package com.bankapp.customerService.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;


@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic customerTopic(){
        return TopicBuilder.name("customer_creation").build();
    }

    @Bean
    public NewTopic loanApplicationTopic(){
        return TopicBuilder.name("loan_application").build();
    }
}

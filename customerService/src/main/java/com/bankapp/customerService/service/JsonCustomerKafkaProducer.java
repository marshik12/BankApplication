package com.bankapp.customerService.service;

import com.bankapp.customerService.model.Customer;
import com.bankapp.customerService.model.LoanRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class JsonCustomerKafkaProducer {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(JsonCustomerKafkaProducer.class);
    private static final String CUSTOMER_CREATION_TOPIC = "customer_creation";
    private static final String LOAN_APPLICATION_TOPIC = "loan_application";

    @Autowired
    private KafkaTemplate<String, Customer> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, LoanRequest> kafkaTemplate1;

    public void sendCustomerCreationMessage(Customer data){

        LOGGER.info(String.format("Message sent -> %s", data.toString()));

        Message<Customer> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, CUSTOMER_CREATION_TOPIC)
                        .build();

        kafkaTemplate.send(message);
    }

    public void sendLoanApplicationMessage(LoanRequest data1){

        LOGGER.info(String.format("Message sent -> %s", data1.toString()));

        Message<LoanRequest> message = MessageBuilder
                .withPayload(data1)
                .setHeader(KafkaHeaders.TOPIC, LOAN_APPLICATION_TOPIC)
                .build();
        kafkaTemplate1.send(LOAN_APPLICATION_TOPIC, data1);
    }

}

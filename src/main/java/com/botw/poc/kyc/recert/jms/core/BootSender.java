package com.botw.poc.kyc.recert.jms.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

/**
 * Created by sharmd01 on 07/21/2017.
 */
public class BootSender {

    private  static final Logger
        LOGGER = LoggerFactory.getLogger(BootSender.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(String destination, String message) {
        LOGGER.info("sending message='{}' to destination='{}'", message, destination);
        jmsTemplate.convertAndSend(destination, message);
    }
}

package com.botw.poc.kyc.recert.jms.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class MessageDelegateImpl implements MessageDelegate {

    private static final Logger LOG = LoggerFactory.getLogger(MessageDelegateImpl.class);

    public void processMessage(TextMessage message) {
        try {
            LOG.info("Consumed text message number {}", message.getStringProperty(message.toString())/*.getIntProperty("messageCount")*/);
           LOG.info("Received message: {}", message.getText());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void processMessage(BytesMessage message) {
        try {
            LOG.info("Consumed bytes message number {}", message.getIntProperty("messageCount"));
        } catch (JMSException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void processMessage(MapMessage message) {
        try {
            LOG.info("Consumed map message number {}", message.getIntProperty("messageCount"));
        } catch (JMSException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void processMesage(ObjectMessage message) {
        try {
            LOG.info("Consumed object message number {}", message.getIntProperty("messageCount"));
        } catch (JMSException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}

package com.botw.poc.kyc.recert.jms.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class MessageDelegate2Impl implements MessageDelegate2 {

    private static final Logger LOG = LoggerFactory.getLogger(MessageDelegate2Impl.class);

    public void processMessage(String message) {
    	LOG.info("Consumed text message number {}", message);
    }

    public void processMessage(byte[] message) {
    	LOG.info("Consumed bytes message number {}", message);
    }

    public void processMessage(Map message) {
    	LOG.info("Consumed map message number {}", message);
    }

    public void processMessage(Object message) {
    	LOG.info("Consumed object message number {}", message);
    }
}

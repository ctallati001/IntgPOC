package com.botw.poc.kyc.recert.jms.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.JMSException;

public class PubSubConsumer {

	private static final Logger LOG = LoggerFactory.getLogger(PubSubConsumer.class);

    /**
     * Run the app and tell the producer to send its messages.
     *
     * @param args
     * @throws JMSException
     */
    public static void main(String[] args) throws JMSException {
    	new ClassPathXmlApplicationContext("/jms/pubsub-consumer-jms-context.xml", PubSubConsumer.class);

        // No need to actually do anything beyond loading the app context because
    	// this starts up the Spring message listener container.
    }
}

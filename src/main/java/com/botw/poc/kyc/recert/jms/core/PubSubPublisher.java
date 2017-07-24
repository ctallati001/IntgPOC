package com.botw.poc.kyc.recert.jms.core;

import com.botw.poc.kyc.recert.jms.producer.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.JMSException;
import java.util.Properties;

public class PubSubPublisher {

	private static final Logger LOG = LoggerFactory.getLogger(PubSubPublisher.class);

	Properties properties = new Properties();
    public static void main(String[] args) throws JMSException {


    	String destinationName = null;
    	String messageType = null;

    	//if (args.length == 2) {
    		destinationName ="TEST.TOPIC";// args[0];
    		messageType = "text";//args[1];
			LOG.debug("Using arguments destinationName: {}  messageType: {}", destinationName, messageType);
	//	} else {
	//	LOG.error("There must be two arguments, destinationName and messageType");
	//	}

    	ApplicationContext context = new ClassPathXmlApplicationContext("/jms/pubsub-producer-jms-context.xml", PubSubPublisher.class);
        MessageProducer producer = (MessageProducer) context.getBean("messageProducer");
        producer.sendMessages(destinationName, messageType);
    }

}

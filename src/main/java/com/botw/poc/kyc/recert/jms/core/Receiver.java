package com.botw.poc.kyc.recert.jms.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;

import java.util.concurrent.CountDownLatch;

/**
 * Created by SHARMD01 on 07/21/2017.
 */
public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @JmsListener(destination = "TEST.FOO")
    public void receive(String message) {
        LOGGER.info("received message='{}'", message);
        latch.countDown();
    }

}

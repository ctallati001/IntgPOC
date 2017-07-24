package com.botw.poc.kyc.recert.jms;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import com.botw.poc.kyc.recert.jms.core.BootSender;
import com.botw.poc.kyc.recert.jms.core.Receiver;
import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.AfterClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by SHARMD01 on 07/21/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.botw.poc.kyc.recert.jms.core.SpringBootJmsApp.class)
public class SpringJmsApplicationTest {

    private static ApplicationContext applicationContext;

    @Autowired
    void setContext(ApplicationContext applicationContext) {
        SpringJmsApplicationTest.applicationContext = applicationContext;
    }

//    @AfterClass
//    public static void afterClass() {
//        ((ConfigurableApplicationContext) applicationContext).close();
//    }

//    @ClassRule
//    public static EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker();

    @Autowired
    private BootSender sender;

    @Autowired
    private Receiver receiver;

    @Test
    public void testReceive() throws Exception {
        sender.send("TEST.FOO", "Hello Spring JMS ActiveMQ!");

        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertThat(receiver.getLatch().getCount()).isEqualTo(0);
    }
}

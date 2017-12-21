package com.spdb.dpib.mq.test;

import javax.jms.Destination;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spdb.ib.dpib.mq.producer.ProducerService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-ibmmq-test.xml")
public class ProducerConsumerTest {

	@Autowired
	private ProducerService producerService;
	@Autowired
	@Qualifier("adapterQueue")
	private Destination adapterQueue;
    
	@Test
    //@Ignore
	public void testMessageSend() {
		boolean b=producerService.sendSingle("Tran888交易====".getBytes(), adapterQueue, true);
		if(b){
			System.out.println("发送消息成功====");
		}else{
			System.out.println("发送消息失败====");
		}
	}
    
}

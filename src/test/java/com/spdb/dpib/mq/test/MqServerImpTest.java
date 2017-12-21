package com.spdb.dpib.mq.test;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spdb.ib.dpib.server.Server;

public class MqServerImpTest {

	public static ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"applicationContext-ibmmq-test.xml");

	@Test
	//@Ignore
	public void testStartServer() throws Exception {
		System.out.println("testing start mqserver listener");
		Server server = (Server) ctx.getBean("mqServer");
		Thread.sleep(50000);
//    	server.stopServer();
//		Thread.sleep(5000);
//		server.init();
//		Thread.sleep(5000);
//		System.out.println("testing start mqserver listener done!");
	}
	@Test
	@Ignore
	public void testRestartServer() throws InterruptedException {
		Server server = (Server)ctx.getBean("mqServer");
		Thread.sleep(5000);
		server.restartServer();
		Thread.sleep(5000);
		server.restartServer();
		Thread.sleep(10000);
		System.out.println("restart mqserver listener done !");
	}
}

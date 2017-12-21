package com.spdb.ib.dpib.server.imp;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spdb.ib.dpib.server.Server;

public class TCPSocketServerImpTest {
	public static ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"applicationContext-comm-test.xml");
	
	@Test
	//@Ignore
	public void testStartServer() throws InterruptedException {
		System.out.println("testing start server");
		Server server = (Server)ctx.getBean("TESTSERVER");
		Thread.sleep(500000);
//		server.stopServer();
//		System.out.println("testing start server done!");
	}

	@Test
	@Ignore
	public void testRestartServer() throws InterruptedException {
		Server server = (Server)ctx.getBean("TESTSERVER");
		Thread.sleep(5000);
		server.restartServer();
		Thread.sleep(5000);
		server.restartServer();
		Thread.sleep(10000);
		System.out.println("restart server done!");
	}
}

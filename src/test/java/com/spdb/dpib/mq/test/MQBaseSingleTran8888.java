package com.spdb.dpib.mq.test;

import com.spdb.ib.dpib.mq.AbstractMQSingleTran;

public class MQBaseSingleTran8888 extends AbstractMQSingleTran<String, String> {
	
	int i = 0;
	/**
	 * 业务操作 包括事物
	 */
	@Override
	public String doTrade(String input) {
		i++;
		System.out.println("input=" + input);
		System.out.println("doTrade i" + i);
		return input;
	}

	@Override
	public void afterReplyDone() {
		i++;
		System.out.println("i=" + i);
		System.out.println("After reply Done");

	}

	@Override
	public void replyException(Exception e) {
		e.printStackTrace();
		i = i++;
		System.out.println("i=" + i);
		System.out.println("reply Exception");

	}
	
}

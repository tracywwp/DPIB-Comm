package com.spdb.dpib.mq.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.spdb.ib.dpib.core.exception.TranException;
import com.spdb.ib.dpib.mq.AbstractMQSingleTran;
import com.spdb.ib.dpib.mq.MQServerTranFactory;

public class ReceiveMsgListener implements MessageListener {
	private MQServerTranFactory mqServerTranFactory;

	@Override
	public void onMessage(Message message) {

		// 这里我们知道生产者发送的就是一个纯文本消息，所以这里可以直接进行强制转换，或者直接把onMessage方法的参数改成Message的子类TextMessage
		TextMessage textMsg = (TextMessage) message;
		System.out.println("接收到一个纯文本消息。");
		
		try {
			System.out.println("消息内容是：" + textMsg.getText() + "JMSMessageID:"
					+ textMsg.getJMSMessageID());
			final AbstractMQSingleTran amt = (AbstractMQSingleTran) mqServerTranFactory
					.getMQServerHandler(textMsg.getText());
			try {
				amt.doTrade(textMsg.getText());
				amt.afterReplyDone();
			} catch (TranException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				amt.replyException(e);
			}

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public MQServerTranFactory getMqServerTranFactory() {
		return mqServerTranFactory;
	}

	public void setMqServerTranFactory(MQServerTranFactory mqServerTranFactory) {
		this.mqServerTranFactory = mqServerTranFactory;
	}

}

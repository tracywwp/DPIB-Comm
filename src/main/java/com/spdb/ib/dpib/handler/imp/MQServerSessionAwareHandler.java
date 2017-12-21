package com.spdb.ib.dpib.handler.imp;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.listener.SessionAwareMessageListener;

import com.spdb.ib.dpib.core.exception.TranException;
import com.spdb.ib.dpib.logs.LogManager;
import com.spdb.ib.dpib.mq.AbstractMQSingleTran;
import com.spdb.ib.dpib.mq.MQServerTranFactory;

/**
 * SessionAwareMessageListener的设计就是为了方便我们在接收到消息后发送一个回复的消息，
 * 它同样为我们提供了一个处理接收到的消息的onMessage方法 ，但是这个方法可以同时接收两个参数，一个是表示当前接收到的消息Message，
 * 另一个就是可以用来发送消息的Session对象
 */
public class MQServerSessionAwareHandler implements
		SessionAwareMessageListener<TextMessage> {
	private MQServerTranFactory mqServerTranFactory;
	private Destination destination;

	public void onMessage(TextMessage message, Session session)
			throws JMSException {
		TextMessage textMsg = (TextMessage) message;
		LogManager.info("接收到一个纯文本消息=====");
		try {
			final AbstractMQSingleTran amt = (AbstractMQSingleTran) mqServerTranFactory
					.getMQServerHandler(textMsg.getText());
			LogManager.info("消息内容是：" + textMsg.getText() + "JMSMessageID:"
					+ textMsg.getJMSMessageID());
			try {
				String desponse = (String) amt.doTrade(textMsg.getText());
				MessageProducer producer = session.createProducer(destination);
				Message textMessage = session.createTextMessage(desponse);
				producer.send(textMessage);
				amt.afterReplyDone();
			} catch (TranException e) {
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

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

}

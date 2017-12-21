package com.spdb.ib.dpib.mq.producer.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.spdb.ib.dpib.logs.LogManager;
import com.spdb.ib.dpib.mq.producer.ProducerService;

/**
 * 
 * @author T-wuwp 向mq队列发送消息
 */
@Component
public class ProducerServiceImpl implements ProducerService {
	// jms 监听器
	private JmsTemplate jmsTemplate;
	private ExecutorService es = Executors.newCachedThreadPool();

	public boolean sendSingle(Object message, Destination destination,
			boolean isSendAsync) {
		return sendMessage(message, destination, isSendAsync);
	}

	private boolean sendMessage(final Object message, Destination destination,
			boolean isSendAsync) {
		// final Destination sendDest = destination;
		boolean sendOk = false;
		// 异步发送消息
		if (isSendAsync) {
			try {
				Callable c1 = new MqSendThread(message, destination);
				Future future = es.submit(c1);
				sendOk = Boolean.parseBoolean(future.get().toString());
			} catch (Exception e) {
				e.printStackTrace();
				sendOk = false;
			}
		} else {
			if (message instanceof String) {
				try {
					send((String) message, destination);
					sendOk = true;
				} catch (Exception e) {
					sendOk = false;
				}
			} else if (message instanceof byte[]) {
				try {
					sendByteMsg((byte[]) message, destination);
					sendOk = true;
				} catch (Exception e) {
					sendOk = false;
				}
			}
		}
		return sendOk;
	}

	// 异步发送消息线程
	private class MqSendThread implements Callable {
		private Object message;
		private Destination sendDest;

		public MqSendThread() {
		}

		public MqSendThread(Object message, Destination sendDest) {
			this.message = message;
			this.sendDest = sendDest;
		}

		@Override
		public Object call() throws Exception {
			boolean f = false;
			if (message instanceof String) {
				try {
					send((String) message, sendDest);
					f = true;
				} catch (Exception e) {
					e.printStackTrace();
					f = false;
				}
			} else if (message instanceof byte[]) {
				try {
					sendByteMsg((byte[]) message, sendDest);
					f = true;
				} catch (Exception e) {
					e.printStackTrace();
					f = false;
				}
			}
			return f;
		}

	}

	//
	private void sendByteMsg(final byte[] message, Destination destination) {
		this.jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				try {
					BytesMessage byteMessage = session.createBytesMessage();
					byteMessage.writeBytes(message);
					return byteMessage;
				} catch (Exception e) {
					e.printStackTrace();
					LogManager.errorOri("---------生产者发送消息失败,消息为：" + message);
					return null;
				}
			}

		});
	}

	private void send(final String message, Destination destination) {
		this.jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				try {
					TextMessage textMessage = session
							.createTextMessage(message);
					return textMessage;
				} catch (Exception e) {
					e.printStackTrace();
					LogManager.errorOri("---------生产者发送消息失败,消息为：" + message);
					return null;
				}
			}

		});
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	@Resource
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

}

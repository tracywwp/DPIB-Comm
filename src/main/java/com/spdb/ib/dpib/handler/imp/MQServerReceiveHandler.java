package com.spdb.ib.dpib.handler.imp;

import java.io.ByteArrayOutputStream;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.spdb.ib.dpib.core.exception.TranException;
import com.spdb.ib.dpib.logs.LogManager;
import com.spdb.ib.dpib.mq.AbstractMQSingleTran;
import com.spdb.ib.dpib.mq.MQServerTranFactory;

public class MQServerReceiveHandler implements MessageListener {
	private MQServerTranFactory mqServerTranFactory;

	@Override
	public void onMessage(Message arg0) {
		if (arg0 instanceof TextMessage) {
			// 这里我们知道生产者发送的就是一个纯文本消息，所以这里可以直接进行强制转换，或者直接把onMessage方法的参数改成Message的子类TextMessage
			TextMessage textMsg = (TextMessage) arg0;
			LogManager.infoOri("接收到一个纯文本消息,消息类型TextMessage=====");
			try {
				LogManager.infoOri("消息内容是：" + textMsg.getText()
						+ "JMSMessageID:" + textMsg.getJMSMessageID());
				doTrade(textMsg.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else if (arg0 instanceof BytesMessage) {
			BytesMessage byteMsg = (BytesMessage) arg0;
			LogManager.infoOri("接收到一个字节数组消息,消息类型BytesMessage=====");
			try {
				ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
				byte[] buffer = new byte[256];
				int length = 0;
				while ((length = byteMsg.readBytes(buffer)) != -1) {
					byteStream.write(buffer, 0, length);
				}
				String context = new String(byteStream.toByteArray());
				LogManager.infoOri("字节数组消息转成字符串消息是：" + context);
				doTrade(context);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 交易逻辑处理
	 * 
	 * @param msg
	 *            mq获取的消息
	 */
	public void doTrade(String msg) {
		final AbstractMQSingleTran amt = (AbstractMQSingleTran) mqServerTranFactory
				.getMQServerHandler(msg);
		try {
			amt.doTrade(msg);
			amt.afterReplyDone();
		} catch (TranException e) {
			amt.replyException(e);
		}
	}

	public MQServerTranFactory getMqServerTranFactory() {
		return mqServerTranFactory;
	}

	public void setMqServerTranFactory(MQServerTranFactory mqServerTranFactory) {
		this.mqServerTranFactory = mqServerTranFactory;
	}

}

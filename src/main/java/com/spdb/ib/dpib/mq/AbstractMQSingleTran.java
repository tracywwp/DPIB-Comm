/**
 * 
 */
package com.spdb.ib.dpib.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.spdb.ib.dpib.core.basetran.imp.AbstractBaseSingleTran;
import com.spdb.ib.dpib.core.queue.imp.QueuePool;
import com.spdb.ib.dpib.core.utils.ObjectLock;

/**
 * @author wangkw MQ
 */
public abstract class AbstractMQSingleTran<INPUT, OUTPUT> extends
		AbstractBaseSingleTran<INPUT, OUTPUT> {
	protected QueuePool queuePool;
	@Autowired
	protected volatile ObjectLock lock;

	/**
	 * mq server回复后的继续操作
	 */
	public abstract void afterReplyDone();
    
	/**
	 * mq server回复客户端超时或者其他异常的操作
	 */
	public abstract void replyException(Exception e);
    
	public QueuePool getQueuePool() {
		return queuePool;
	}

	public void setQueuePool(QueuePool queuePool) {
		this.queuePool = queuePool;
	}

	public ObjectLock getLock() {
		return lock;
	}

	public void setLock(ObjectLock lock) {
		this.lock = lock;
	}

}

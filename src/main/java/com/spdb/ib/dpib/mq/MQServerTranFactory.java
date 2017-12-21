package com.spdb.ib.dpib.mq;

/**
 * @author wangkw
 *
 */
public interface MQServerTranFactory {
	/**
	 * @author wangkw
	 * 根据不同的交易代码返回对应的MQServer
	 */
	public AbstractMQSingleTran getMQServerHandler(String textMsg);
}

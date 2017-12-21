package com.spdb.ib.dpib.factory;

import java.util.HashMap;

import com.spdb.ib.dpib.common.SpringContextUtil;
import com.spdb.ib.dpib.mq.AbstractMQSingleTran;
import com.spdb.ib.dpib.mq.MQServerTranFactory;
import com.spdb.ib.dpib.mq.MsgDecoder;

/**
 * @author wangkw TBD
 */
public class MQServerTranFactoryImp implements MQServerTranFactory {
	/**
	 * 获取交易码接口
	 */
	MsgDecoder msgDecoder;
	HashMap<String, String> mqTranMap;

	public AbstractMQSingleTran getMQServerHandler(String textMsg) {
		return (AbstractMQSingleTran) SpringContextUtil.getBean(mqTranMap
				.get(msgDecoder.getTranCode(textMsg)));
	}

	public HashMap<String, String> getMqTranMap() {
		return mqTranMap;
	}

	public void setMqTranMap(HashMap<String, String> mqTranMap) {
		this.mqTranMap = mqTranMap;
	}

	public MsgDecoder getMsgDecoder() {
		return msgDecoder;
	}

	public void setMsgDecoder(MsgDecoder msgDecoder) {
		this.msgDecoder = msgDecoder;
	}

}

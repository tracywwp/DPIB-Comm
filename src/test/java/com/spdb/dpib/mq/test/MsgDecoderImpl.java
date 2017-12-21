package com.spdb.dpib.mq.test;

import com.spdb.ib.dpib.mq.MsgDecoder;

public class MsgDecoderImpl implements MsgDecoder {

	@Override
	public String getTranCode(String tranCode) {
		return "8888";
	}

}

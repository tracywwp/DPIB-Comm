package com.spdb.ib.dpib.mq.producer;

import javax.jms.Destination;

public interface ProducerService {
	boolean sendSingle(Object message, Destination destination,boolean isSendAsync);
}

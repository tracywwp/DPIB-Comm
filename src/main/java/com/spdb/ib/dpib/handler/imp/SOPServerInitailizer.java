/**
 * 
 */
package com.spdb.ib.dpib.handler.imp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;



/**
 * @author wangkw
 * SOP初始化
 */
public class SOPServerInitailizer extends ChannelInitializer {
	SOPServerHandler sOPServerHandler;
	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new DatagramPrinter());
		pipeline.addLast(sOPServerHandler);
	}
	public SOPServerHandler getsOPServerHandler() {
		return sOPServerHandler;
	}
	public void setsOPServerHandler(SOPServerHandler sOPServerHandler) {
		this.sOPServerHandler = sOPServerHandler;
	}
	
}

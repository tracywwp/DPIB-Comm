package com.spdb.ib.dpib.handler.imp;

import com.spdb.ib.dpib.logs.LogManager;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class DatagramPrinter extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		LogManager.debugOri("entering DatagramPrinter");
		ByteBuf buf = (ByteBuf)msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        LogManager.debugOri("请求报文开始 start:");
        LogManager.debugOri("[");
		for(int i =0 ;i<req.length;i++){
			LogManager.infoOri(req[i]+" ");
		}
		LogManager.debugOri("]");
		LogManager.debugOri("请求报文结束 end");
		ctx.fireChannelRead(req);
	}
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
			LogManager.errorOri("exception Caught!",(Exception)cause);
    		cause.printStackTrace();
    		ctx.close();
    }
	
}

package com.spdb.dpib.comm.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable 
public class CopyOfCustomServerHandler2 extends ChannelInboundHandlerAdapter{
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] req = (byte[]) msg;
        System.out.println("req = "+req[0]+"  "+req[1]);
        ctx.fireChannelRead(msg);
    }
	
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    }
	
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
    		System.out.println("exception Caught!");
    		cause.printStackTrace();
    }
}

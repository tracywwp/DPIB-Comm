package com.spdb.dpib.comm.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class CustomServerHandler extends ChannelInboundHandlerAdapter{
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	System.out.println("entering read");
        byte[] req = (byte[]) msg;
        System.out.println("req = "+req[0]+"  "+req[1]);
        ctx.fireChannelRead(msg);
    }
	
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    	System.out.println("entering read complete!");
    	  byte[] resp = new byte[2];
    	  resp[0]='3';
    	  resp[1]='4';
        ByteBuf respBuf =  Unpooled.copiedBuffer(resp);
        ctx.writeAndFlush(respBuf).addListener(ChannelFutureListener.CLOSE);
    }
	
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
    		System.out.println("exception Caught!");
    		cause.printStackTrace();
    }
}
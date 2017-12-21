/**
 * 
 */
package com.spdb.dpib.comm.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import com.spdb.ib.dpib.handler.imp.DatagramPrinter;

/**
 * @author wangkw
 *
 */
public class NettyServer {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
	     EventLoopGroup bossGroup = new NioEventLoopGroup(10);
	     EventLoopGroup workerGroup = new NioEventLoopGroup(10);
	        try {
	            ServerBootstrap b = new ServerBootstrap();
	            b.group(bossGroup, workerGroup)
	             .channel(NioServerSocketChannel.class)
	             .handler(new LoggingHandler(LogLevel.INFO))
	             .childHandler(new DatagramPrinter());
//	             .childHandler(childHandler)
	            b.bind(8848).sync().channel().closeFuture().sync();
	            
	        } finally {
	            bossGroup.shutdownGracefully();
	            workerGroup.shutdownGracefully();
	        }
	}

}

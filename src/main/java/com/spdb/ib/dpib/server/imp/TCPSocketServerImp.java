/**
 * 
 */
package com.spdb.ib.dpib.server.imp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.spdb.ib.dpib.logs.LogManager;
import com.spdb.ib.dpib.server.Server;

/**
 * @author wangkw TCPSocetServer
 */
public class TCPSocketServerImp implements Server, Runnable {

	private int bossThreadNum;
	private int workerThreadNum;
	private int port;
	private Channel channel;
	private ChannelInitializer channelInitializer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spdb.ib.dpib.server.Server#startServer()
	 */
	public void init() {
		LogManager.infoOri("Server init!!");
		Thread serverThread = new Thread(this);
		serverThread.start();
	}
    
	@Override
	public void run() {
		LogManager.infoOri("Server running!");
		startServer();
	}

	@Override
	public void startServer() {
		// TODO Auto-generated method stub
		System.out.println("this is"+this);
		EventLoopGroup bossGroup = new NioEventLoopGroup(bossThreadNum);
		EventLoopGroup workerGroup = new NioEventLoopGroup(workerThreadNum);
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(channelInitializer);
		// .option(ChannelOption.SO_REUSEADDR, true);
		// .handler(new LoggingHandler(LogLevel.INFO))
		try {
			channel = b.bind(port).sync().channel();
			LogManager.infoOri("Server at Port" + port + "started");
		} catch (InterruptedException e) {
			LogManager.errorOri("Server at Port" + port + "start failed", e);
		}
		try {
			channel.closeFuture().sync();
			LogManager.infoOri("Server at Port" + port + "stoped");
		} catch (InterruptedException e) {
			LogManager.errorOri("Server at Port" + port + "stop failed", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spdb.ib.dpib.server.Server#stopServer()
	 */
	@Override
	public void stopServer() {
		LogManager.infoOri("stoping the server");
		channel.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spdb.ib.dpib.server.Server#restartServer()
	 */
	@Override
	public void restartServer() {
		LogManager.infoOri("restarting  the server");
		try {
			stopServer();
		} finally {
			init();
		}
	}

	public int getBossThreadNum() {
		return bossThreadNum;
	}

	public void setBossThreadNum(int bossThreadNum) {
		this.bossThreadNum = bossThreadNum;
	}

	public int getWorkerThreadNum() {
		return workerThreadNum;
	}

	public void setWorkerThreadNum(int workerThreadNum) {
		this.workerThreadNum = workerThreadNum;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public ChannelInitializer getChannelInitializer() {
		return channelInitializer;
	}

	public void setChannelInitializer(ChannelInitializer channelInitializer) {
		this.channelInitializer = channelInitializer;
	}

}

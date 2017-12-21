/**
 * 
 */
package com.spdb.ib.dpib.server.imp;

import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.spdb.ib.dpib.logs.LogManager;
import com.spdb.ib.dpib.server.Server;

/**
 * MQ Server Imp
 * WWP TD
 *
 */
public class MQServerImp extends DefaultMessageListenerContainer implements Server, Runnable{
    
	@Override
	public void run() {
		LogManager.infoOri("MQServer listener running!");
		startServer();
	}
    
	/**
	 * 启动监听器
	 */
	@Override
	public void startServer() {
		start();
	}
	
	/**
	 * 初始化监听器
	 */
	@Override
	public void init() {
		LogManager.infoOri("MQServer listener init!!");
		Thread serverThread = new Thread(this);
		serverThread.start();
		
	}
	/**
	 * 停止监听器
	 */
	@Override
	public void stopServer() {
		stop();
	}
	/**
	 * 重启监听器
	 */
	@Override
	public void restartServer() {
		LogManager.infoOri("restarting  the MQserver listener");
		try {
			stopServer();
		} finally {
			init();
		}
		
	}


}

package com.spdb.ib.dpib.server;
/**
 * @author wangkw
 *  Server接口
 */
public interface Server{
	public abstract void startServer();
	public abstract void init();
	public abstract void stopServer();
	public abstract void restartServer();
}

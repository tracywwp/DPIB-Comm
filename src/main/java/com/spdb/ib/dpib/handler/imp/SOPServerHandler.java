/**
 * 
 */
package com.spdb.ib.dpib.handler.imp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import com.spdb.ib.dpib.common.SpringContextUtil;
import com.spdb.ib.dpib.dto.InDto;
import com.spdb.ib.dpib.dto.OutDto;
import com.spdb.ib.dpib.logs.LogManager;
import com.spdb.ib.dpib.services.SOPFreemarkerService;
import com.spdb.ib.dpib.sop.AbstractSOPSingleTran;
import com.spdb.ib.dpib.sop.SOPServerTranFactory;

/**
 * @author wangkw SOP Server处理类
 */
@Sharable
public class SOPServerHandler extends ChannelInboundHandlerAdapter {
	private SOPServerTranFactory sOPServerTranFactory;
	private SOPFreemarkerService sOPFreemarkerService;
	private int sync_flag = 0;
	long timeout = 180;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		OutDto outDto;
		SOPFreemarkerService sOPFreemarkerService = (SOPFreemarkerService) SpringContextUtil.getBean("sOPFreemarkerService");
		InDto inDto = (InDto) sOPFreemarkerService.covertBytesToObject((byte[]) msg);
		final AbstractSOPSingleTran aspt = (AbstractSOPSingleTran) sOPServerTranFactory.getSOPServerHandler(inDto.getTransCode());
		aspt.setInput(inDto);
		/**
		 * 判断是同步提交还是异步提交
		 */
		outDto = (OutDto) (sync_flag == 0 ? aspt.doTrade(inDto) : aspt.submitTrade(inDto));
		aspt.setOutput(outDto);
		byte[] resp = sOPFreemarkerService.covertDtoToBytes(outDto);
		LogManager.debug("[" + new String(resp) + "]");
		ByteBuf respBuf = Unpooled.copiedBuffer(resp);
		ctx.writeAndFlush(respBuf).addListener(ChannelFutureListener.CLOSE).addListeners(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) {
				try {
					aspt.afterReplyDone();// 正常完成后继续执行的业务
				} catch (Exception e) {
					aspt.replyException(e);
				} finally {
					future.channel().close();
				}
			}
		});
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		try {
			LogManager.errorOri(" SOPServerHandler exception Caught!",(Exception)cause);
			cause.printStackTrace();
		} finally {
			ctx.channel().close();
		}
	}

	public SOPServerTranFactory getsOPServerTranFactory() {
		return sOPServerTranFactory;
	}

	public void setsOPServerTranFactory(SOPServerTranFactory sOPServerTranFactory) {
		this.sOPServerTranFactory = sOPServerTranFactory;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public SOPFreemarkerService getsOPFreemarkerService() {
		return sOPFreemarkerService;
	}

	public void setsOPFreemarkerService(SOPFreemarkerService sOPFreemarkerService) {
		this.sOPFreemarkerService = sOPFreemarkerService;
	}

	public int getSync_flag() {
		return sync_flag;
	}

	public void setSync_flag(int sync_flag) {
		this.sync_flag = sync_flag;
	}
}

/**
 * 
 */
package com.spdb.ib.dpib.sop;

import com.spdb.ib.dpib.core.basetran.imp.AbstractBaseSingleTran;
import com.spdb.ib.dpib.dto.InDto;
import com.spdb.ib.dpib.dto.OutDto;

/**
 * @author wangkw
 *
 */
public abstract class AbstractSOPSingleTran<InDtoEx extends InDto,OutDtoEx extends OutDto> extends AbstractBaseSingleTran<InDtoEx,OutDtoEx> {
	/**
	 * SOP做server回复后的继续操作
	 */
	public abstract void afterReplyDone();
	/**
	 * SOP做server回复客户端超时或者其他异常的操作 
	 */
	public abstract void replyException(Exception e);
	
}


/**
 * 
 */
package com.spdb.dpib.sop.server.test;

import com.spdb.ib.dpib.core.exception.TranException;
import com.spdb.ib.dpib.dto.InDto3691;
import com.spdb.ib.dpib.dto.OutDto3691;
import com.spdb.ib.dpib.sop.AbstractSOPSingleTran;

/**
 * 
 * @author wangkw
 * 3691方法
 */
public class SOPBaseSingleTran3691 extends AbstractSOPSingleTran<InDto3691,OutDto3691>{
	int i = 0;

	@Override
	public void afterReplyDone() {
		i++;
		System.out.println("i="+output.getAccountNo());
		System.out.println("After reply Done");
	}

	@Override
	public void replyException(Exception e) {
		e.printStackTrace();
		i = i++;
		System.out.println("i="+output.getAccountNo());
		System.out.println("reply Exception");
	}

	@Override
	public OutDto3691 doTrade(InDto3691 input) throws TranException {
		// TODO Auto-generated method stub
		System.out.println("input = "+input.getTransCode());
		OutDto3691 outDto = new OutDto3691();
		outDto.setAccountFlag("1");
		outDto.setAccountNo("6225223780000059");
		outDto.setCoreDate("20140106");
		outDto.setTransCode("3691");
		outDto.setBalanceAmount("99.99");
		outDto.setOperatorNum("99955001");
		outDto.setReturnCode("AAAAAAA");
		outDto.setReturnMsg("3691測試成功");
		return outDto;
	}

}

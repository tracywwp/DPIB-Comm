/**
 * 
 */
package com.spdb.ib.dpib.factory;

import java.util.HashMap;

import com.spdb.ib.dpib.common.SpringContextUtil;
import com.spdb.ib.dpib.sop.AbstractSOPSingleTran;
import com.spdb.ib.dpib.sop.SOPServerTranFactory;

/**
 * @author wangkw
 *	SOP交易的工厂实现
 */
public class SOPServerTranFactoryImp implements SOPServerTranFactory {

	HashMap<String, String> sopTranMap ;
	/* (non-Javadoc)
	 * @see com.spdb.ib.dpib.server.SOPServerTranFactory#getSOPServerHandler(java.lang.String)
	 * 返回对应的交易码
	 */
	@Override
	public AbstractSOPSingleTran getSOPServerHandler(String trancode) {
		// TODO Auto-generated method stub
		return (AbstractSOPSingleTran) SpringContextUtil.getBean(sopTranMap.get(trancode));
	}
	public HashMap<String, String> getSopTranMap() {
		return sopTranMap;
	}
	public void setSopTranMap(HashMap<String, String> sopTranMap) {
		this.sopTranMap = sopTranMap;
	}

}

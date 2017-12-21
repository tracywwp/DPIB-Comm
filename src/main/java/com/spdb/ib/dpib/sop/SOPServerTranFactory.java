/**
 * 
 */
package com.spdb.ib.dpib.sop;



/**
 * @author wangkw
 * 根据不同的交易代码返回对应的SOPServer
 */
public interface SOPServerTranFactory {
	public AbstractSOPSingleTran getSOPServerHandler(String trancode);
}

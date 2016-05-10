package com.allinfinance.bo.settle;

import java.io.File;
import java.util.List;

import com.allinfinance.vo.T8073201VO;

/**
 * 差错调账处理业务对象接口类
 * 
 * @author luhq
 *
 */
public interface T80732BO {
	/**
	 * 差错交易调账处理
	 * 
	 * @param t8073201VO 调账记录信息
	 * @return 调账处理结果 0:处理成功、1:已调账、99：其他错误
	 */
	public int ajust(T8073201VO t8073201VO);
	public int ajustByHand(T8073201VO t8073201VO,String imgBatchId);	//手工调账
	public String upload(List<File> imgFile, List<String> imgFileFileName, String settleDate, String keyInst,String oprId,String imgBatchId);
}
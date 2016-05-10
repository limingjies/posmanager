package com.allinfinance.dwr.term;

import org.apache.log4j.Logger;

import com.allinfinance.bo.term.TblTermKeyBO;
import com.allinfinance.po.TblTermKeyPK;
import com.allinfinance.system.util.ContextUtil;

public class T30204 {

	private TblTermKeyBO tblTermKeyBO = (TblTermKeyBO) ContextUtil.getBean("TblTermKeyBO");
	private TblTermKeyPK tblTermKeyPK;
	private static Logger log = Logger.getLogger(T30204.class);
	
	public String deleteTermTmk(String checkedTerm){
		try{
			Object[] obj = checkedTerm.split("#");
			for(int i=0;i<obj.length;i++){
				String mchtId = obj[i].toString().split("@")[0];
				String termId = obj[i].toString().split("@")[1];
				tblTermKeyPK = new TblTermKeyPK();
				tblTermKeyPK.setMchtCd(mchtId);
				tblTermKeyPK.setTermId(termId);
				tblTermKeyBO.delete(tblTermKeyPK);
				log.info("mchtId:" + mchtId+"已经删除");
				log.info("termId:" + termId+"已经删除");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "success";
	}

	public TblTermKeyBO getTblTermKeyBO() {
		return tblTermKeyBO;
	}

	public void setTblTermKeyBO(TblTermKeyBO tblTermKeyBO) {
		this.tblTermKeyBO = tblTermKeyBO;
	}

	/**
	 * @return the tblTermKeyPK
	 */
	public TblTermKeyPK getTblTermKeyPK() {
		return tblTermKeyPK;
	}

	/**
	 * @param tblTermKeyPK the tblTermKeyPK to set
	 */
	public void setTblTermKeyPK(TblTermKeyPK tblTermKeyPK) {
		this.tblTermKeyPK = tblTermKeyPK;
	}
}

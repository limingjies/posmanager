package com.allinfinance.bo.mchnt;

import java.util.List;

import com.allinfinance.po.mchnt.TblHoldMchtParticipat;
import com.allinfinance.po.mchnt.TblHoldMchtParticipatPK;

public interface T20802BO {

	public TblHoldMchtParticipat get(TblHoldMchtParticipatPK id);
	
	public String add(TblHoldMchtParticipat tblHoldMchtParticipat);

	public String update(List<TblHoldMchtParticipat> tblHoldMchtParticipatList);
	
	public String cancel(TblHoldMchtParticipat tblHoldMchtParticipat);
	
	public String delete(List<TblHoldMchtParticipatPK> idList);
}

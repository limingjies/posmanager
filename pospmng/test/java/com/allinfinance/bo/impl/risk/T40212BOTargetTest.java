package com.allinfinance.bo.impl.risk;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.risk.TblBankCardBlackDAO;
import com.allinfinance.dao.iface.risk.TblBankCardBlackOptLogDAO;
import com.allinfinance.po.TblRiskInfUpdLog;
import com.allinfinance.po.risk.TblBankCardBlack;
import com.allinfinance.po.risk.TblBankCardBlackOptLog;

@RunWith(MockitoJUnitRunner.class)
public class T40212BOTargetTest {

	@Mock
	private	 TblBankCardBlackDAO tblBankCardBlackDAO;
	@Mock
	private	 TblBankCardBlackOptLogDAO tblBankCardBlackOptLogDAO;
	@Mock
	private ICommQueryDAO commQueryDAO;
	@Mock
	private TblBankCardBlack tblBankCardBlack;
	@Mock
	private TblBankCardBlackOptLog tblBankCardBlackOptLog;
	@Mock
	private TblRiskInfUpdLog tblRiskInfUpdLog;
	@Mock
	private Operator operator;
	
	@InjectMocks
	private T40212BOTarget t40212BO;

	private String defValue = "0";
	private String chaValue = "11111111";

	@Before
	public void setup() {
		when(tblBankCardBlack.getCardNo()).thenReturn(defValue);
		when(tblBankCardBlack.getRemarkInfo()).thenReturn(defValue);
	}

	@Test
	public void testGet() throws Exception {
		t40212BO.get(defValue);
		verify(tblBankCardBlackDAO).get(defValue);
	}

	@Test
	public void testQuery() throws Exception {
		t40212BO.query(chaValue);
		verify(commQueryDAO).findBySQLQuery(any(String.class));
	}
/*
	@Test
	public void testAdd() throws Exception {
		String retCode = t40212BO.add(tblBankCardBlack);
		verify(commQueryDAO).excute(chaValue);
		verify(tblBankCardBlackDAO).save(tblBankCardBlack);
		verify(tblBankCardBlackOptLogDAO).save(any(TblBankCardBlackOptLog.class));
		assertEquals(Constants.SUCCESS_CODE, retCode);
	}

	@Test
	public void testDelete() throws Exception {
		String retCode = t40212BO.delete(defValue,operator);
		verify(tblBankCardBlackDAO).get(defValue);
		verify(tblBankCardBlackOptLogDAO).save(tblBankCardBlackOptLog);
		verify(tblBankCardBlackDAO).delete(defValue);
		assertEquals(Constants.SUCCESS_CODE, retCode);
	}
*/
}

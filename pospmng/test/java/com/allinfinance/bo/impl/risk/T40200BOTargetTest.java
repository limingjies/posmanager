package com.allinfinance.bo.impl.risk;

import static org.junit.Assert.assertEquals;
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
import com.allinfinance.dao.iface.risk.TblRiskInfUpdLogDAO;
import com.allinfinance.dao.iface.risk.TblRiskParamDefDAO;
import com.allinfinance.po.TblRiskInfUpdLog;
import com.allinfinance.po.risk.TblRiskParamDef;
import com.allinfinance.po.risk.TblRiskParamDefPK;

@RunWith(MockitoJUnitRunner.class)
public class T40200BOTargetTest {

	@Mock
	private TblRiskParamDefDAO tblRiskParamDefDAO;
	@Mock
	private TblRiskInfUpdLogDAO tblRiskInfUpdLogDAO;
	@Mock
	private TblRiskParamDef tblRiskParamDefNew;
	@Mock
	private TblRiskParamDef tblRiskParamDefOld;
	@Mock
	private TblRiskInfUpdLog tblRiskInfUpdLog;
	@Mock
	private Operator operator;
	@Mock
	private TblRiskParamDefPK key;
	
	@InjectMocks
	private T40200BOTarget t40200BO;

	private String defValue = "0";
	private String chaValue = "1";

	@Before
	public void setup() {
		when(tblRiskParamDefOld.getDefaultValue()).thenReturn(defValue);
		when(tblRiskParamDefNew.getDefaultValue()).thenReturn(defValue);
		when(tblRiskParamDefNew.getId()).thenReturn(key);
		when(key.getRiskId()).thenReturn(defValue);
	}

	@Test
	public void testUpdate() throws Exception {
		String retCode = t40200BO.update(tblRiskParamDefNew,tblRiskParamDefOld,operator);
//		verify(tblRiskInfUpdLogDAO).save(tblRiskInfUpdLog);
		verify(tblRiskParamDefDAO).update(tblRiskParamDefNew);
		assertEquals(Constants.SUCCESS_CODE, retCode);
	}

	@Test
	public void testGet() throws Exception {
		t40200BO.get(key);
		verify(tblRiskParamDefDAO).get(key);
	}

}

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
import com.allinfinance.common.RiskConstants;
import com.allinfinance.dao.iface.risk.TblWhiteMchtApplyDAO;
import com.allinfinance.dao.iface.risk.TblWhiteMchtCheckDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtBaseInfDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtBaseInfTmpDAO;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.risk.TblWhiteMchtApply;
import com.allinfinance.po.risk.TblWhiteMchtCheck;

@RunWith(MockitoJUnitRunner.class)
public class T40401BOTargetTest {

	@Mock
	private TblWhiteMchtApplyDAO tblWhiteMchtApplyDAO;
	@Mock
	private TblWhiteMchtCheckDAO tblWhiteMchtCheckDAO;
	@Mock
	private TblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO;
	@Mock
	private TblMchtBaseInfDAO tblMchtBaseInfDAO;
	@Mock
	private TblWhiteMchtCheck tblWhiteMchtCheck;
	@Mock
	private TblWhiteMchtApply tblWhiteMchtApply;
	@Mock
	private TblMchtBaseInfTmp tblMchtBaseInfTmp;
	@Mock
	private TblMchtBaseInf tblMchtBaseInf;
	
	@InjectMocks
	private T40401BOTarget t40401BO;

	private String id = "0";

	@Before
	public void setup() {
		when(tblWhiteMchtApply.getCheckStatus()).thenReturn(RiskConstants.LAST_CHECK_T);
		when(tblWhiteMchtApply.getMchtNo()).thenReturn(id);
		when(tblMchtBaseInfTmpDAO.get(id)).thenReturn(tblMchtBaseInfTmp);
		when(tblMchtBaseInfDAO.get(id)).thenReturn(tblMchtBaseInf);
	}

	@Test
	public void testGet() throws Exception {
		t40401BO.get(id);
		verify(tblWhiteMchtApplyDAO).get(id);
	}

	@Test
	public void testLoad() throws Exception {
		t40401BO.load(id);
		verify(tblMchtBaseInfDAO).get(id);
	}

	@Test
	public void testCheck() throws Exception {
		String retCode = t40401BO.check(tblWhiteMchtCheck,tblWhiteMchtApply);
		verify(tblWhiteMchtApplyDAO).update(tblWhiteMchtApply);
		verify(tblWhiteMchtCheckDAO).save(tblWhiteMchtCheck);
		verify(tblMchtBaseInfTmpDAO).get(id);
		verify(tblMchtBaseInfDAO).get(id);
		verify(tblMchtBaseInfTmpDAO).update(tblMchtBaseInfTmp);
		verify(tblMchtBaseInfDAO).update(tblMchtBaseInf);
		assertEquals(Constants.SUCCESS_CODE, retCode);
	}

}

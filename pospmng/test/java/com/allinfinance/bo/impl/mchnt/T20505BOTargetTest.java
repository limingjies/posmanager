package com.allinfinance.bo.impl.mchnt;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.mchnt.TblFirstMchtTxnDAO;
import com.allinfinance.po.mchnt.TblFirstMchtTxn;

@RunWith(MockitoJUnitRunner.class)
public class T20505BOTargetTest {

	@Mock
	private TblFirstMchtTxnDAO tblFirstMchtTxnDAO;
	@Mock
	private TblFirstMchtTxn tblFirstMchtTxn;
	
	@InjectMocks
	private T20505BOTarget t20505BO;
	
	private String id;
	
	@Before
	public void setup() {
	}

	@Test
	public void testGet() throws Exception {
		t20505BO.get(id);
		verify(tblFirstMchtTxnDAO).get(id);
	}

	@Test
	public void testAdd() throws Exception {
		String retCode = t20505BO.add(tblFirstMchtTxn);
		verify(tblFirstMchtTxnDAO).save(tblFirstMchtTxn);
		assertEquals(Constants.SUCCESS_CODE, retCode);
	}

	@Test
	public void testUpdate() throws Exception {
		String retCode = t20505BO.update(tblFirstMchtTxn);
		verify(tblFirstMchtTxnDAO).update(tblFirstMchtTxn);
		assertEquals(Constants.SUCCESS_CODE, retCode);
	}

	@Test
	public void testDelete1() throws Exception {
		String retCode = t20505BO.delete(tblFirstMchtTxn);
		verify(tblFirstMchtTxnDAO).delete(tblFirstMchtTxn);
		assertEquals(Constants.SUCCESS_CODE, retCode);
	}

	@Test
	public void testDelete2() throws Exception {
		String retCode = t20505BO.delete(id);
		verify(tblFirstMchtTxnDAO).delete(id);
		assertEquals(Constants.SUCCESS_CODE, retCode);
	}

}

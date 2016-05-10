package com.allinfinance.bo.impl.settle;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.allinfinance.common.Constants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.settle.TblChannelCnapsMapDAO;
import com.allinfinance.po.settle.TblChannelCnapsMap;

@RunWith(MockitoJUnitRunner.class)
public class T80902BOTargetTest {

	@Mock
	private TblChannelCnapsMapDAO tblChannelCnapsMapDAO;
	@Mock
	private ICommQueryDAO commQueryDAO;
	@Mock
	private TblChannelCnapsMap tblChannelCnapsMap;
	
	@InjectMocks
	private T80902BOTarget t80902BO;
	
	private String id;
	
	@Before
	public void setup() {
	}

	@Test
	public void testGet() throws Exception {
		t80902BO.get(id);
		verify(tblChannelCnapsMapDAO).get(id);
	}

	@Test
	public void testAdd() throws Exception {
		String retCode = t80902BO.add(tblChannelCnapsMap);
		verify(tblChannelCnapsMapDAO).save(tblChannelCnapsMap);
		assertEquals(Constants.SUCCESS_CODE, retCode);
	}

	@Test
	public void testUpdate() throws Exception {
		String retCode = t80902BO.update(tblChannelCnapsMap);
		verify(tblChannelCnapsMapDAO).update(tblChannelCnapsMap);
		assertEquals(Constants.SUCCESS_CODE, retCode);
	}

	@Test
	public void testDelete() throws Exception {
		String retCode = t80902BO.delete(id);
		verify(tblChannelCnapsMapDAO).delete(id);
		assertEquals(Constants.SUCCESS_CODE, retCode);
	}

	@Test
	public void testBatchDel() throws Exception {
		String retCode = t80902BO.batchDel(id);
		verify(commQueryDAO).excute(any(String.class));
		assertEquals(Constants.SUCCESS_CODE, retCode);
	}

}

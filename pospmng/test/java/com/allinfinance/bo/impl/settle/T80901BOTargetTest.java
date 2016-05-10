package com.allinfinance.bo.impl.settle;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.settle.TblPayChannelInfoDAO;
import com.allinfinance.po.settle.TblPayChannelInfo;

@RunWith(MockitoJUnitRunner.class)
public class T80901BOTargetTest {

	@Mock
	private TblPayChannelInfoDAO tblPayChannelInfoDAO;
	@Mock
	private TblPayChannelInfo tblPayChannelInfo;
	
	@InjectMocks
	private T80901BOTarget t80901BO;
	
	private String id;
	
	@Before
	public void setup() {
	}

	@Test
	public void testGet() throws Exception {
		t80901BO.get(id);
		verify(tblPayChannelInfoDAO).get(id);
	}

	@Test
	public void testAdd() throws Exception {
		String retCode = t80901BO.add(tblPayChannelInfo);
		verify(tblPayChannelInfoDAO).save(tblPayChannelInfo);
		assertEquals(Constants.SUCCESS_CODE, retCode);
	}

	@Test
	public void testUpdate() throws Exception {
		String retCode = t80901BO.update(tblPayChannelInfo);
		verify(tblPayChannelInfoDAO).update(tblPayChannelInfo);
		assertEquals(Constants.SUCCESS_CODE, retCode);
	}

	@Test
	public void testDelete() throws Exception {
		String retCode = t80901BO.delete(id);
		verify(tblPayChannelInfoDAO).delete(id);
		assertEquals(Constants.SUCCESS_CODE, retCode);
	}

}

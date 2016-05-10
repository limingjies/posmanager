package com.allinfinance.bo.impl.risk;

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
import com.allinfinance.dao.iface.risk.TblGreyMchtSortDAO;
import com.allinfinance.dao.iface.risk.TblRiskGreyMchtDAO;
import com.allinfinance.po.risk.TblGreyMchtSort;
import com.allinfinance.po.risk.TblRiskGreyMcht;
import com.allinfinance.po.risk.TblRiskGreyMchtPK;

@RunWith(MockitoJUnitRunner.class)
public class T40400BOTargetTest {

	@Mock
	private TblGreyMchtSortDAO tblGreyMchtSortDAO;
	@Mock
	private TblRiskGreyMchtDAO tblRiskGreyMchtDAO;
	@Mock
	private ICommQueryDAO commQueryDAO;
	@Mock
	private TblGreyMchtSort tblGreyMchtSort;
	@Mock
	private TblRiskGreyMcht tblRiskGreyMcht;
	@Mock
	private TblRiskGreyMchtPK key;
	
	@InjectMocks
	private T40400BOTarget t40400BO;

	private String id = "1";

	@Before
	public void setup() {
	}

	@Test
	public void testQuery() throws Exception {
		t40400BO.query(id);
		verify(commQueryDAO).findCountBySQLQuery(any(String.class));
	}

	@Test
	public void testGetSort() throws Exception {
		t40400BO.getSort(id);
		verify(tblGreyMchtSortDAO).get(id);
	}

	@Test
	public void testAddSort() throws Exception {
		String retCode = t40400BO.addSort(tblGreyMchtSort);
		verify(tblGreyMchtSortDAO).save(tblGreyMchtSort);
		assertEquals(Constants.SUCCESS_CODE, retCode);
	}

	@Test
	public void testUpdateSort() throws Exception {
		String retCode = t40400BO.updateSort(tblGreyMchtSort);
		verify(tblGreyMchtSortDAO).update(tblGreyMchtSort);
		assertEquals(Constants.SUCCESS_CODE, retCode);
	}

	@Test
	public void testDeleteSort() throws Exception {
		String retCode = t40400BO.deleteSort(id);
		verify(tblGreyMchtSortDAO).delete(id);
		assertEquals(Constants.SUCCESS_CODE, retCode);
	}

	@Test
	public void testGet() throws Exception {
		t40400BO.get(key);
		verify(tblRiskGreyMchtDAO).get(key);
	}

	@Test
	public void testAdd() throws Exception {
		String retCode = t40400BO.add(tblRiskGreyMcht);
		verify(tblRiskGreyMchtDAO).save(tblRiskGreyMcht);
		assertEquals(Constants.SUCCESS_CODE, retCode);
	}

	@Test
	public void testUpdate() throws Exception {
		String retCode = t40400BO.update(tblRiskGreyMcht);
		verify(tblRiskGreyMchtDAO).update(tblRiskGreyMcht);
		assertEquals(Constants.SUCCESS_CODE, retCode);
	}

	@Test
	public void testDelete() throws Exception {
		String retCode = t40400BO.delete(key);
		verify(tblRiskGreyMchtDAO).delete(key);
		assertEquals(Constants.SUCCESS_CODE, retCode);
	}

}

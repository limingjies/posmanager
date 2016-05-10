package com.allinfinance.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblRiskLvl implements Serializable{

	// primary key
		private TblRiskLvlPK id;

		// fields
		private java.lang.String resved;
		private java.lang.String resved1;
		public TblRiskLvlPK getId() {
			return id;
		}
		public void setId(TblRiskLvlPK id) {
			this.id = id;
		}
		public java.lang.String getResved() {
			return resved;
		}
		public void setResved(java.lang.String resved) {
			this.resved = resved;
		}
		public java.lang.String getResved1() {
			return resved1;
		}
		public void setResved1(java.lang.String resved1) {
			this.resved1 = resved1;
		}
		public TblRiskLvl() {
		}
		
		
}

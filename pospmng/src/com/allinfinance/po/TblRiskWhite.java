package com.allinfinance.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblRiskWhite implements Serializable{

	// primary key
		private TblRiskWhitePK id;

		// fields
		private java.lang.String resved;
		private java.lang.String resved1;
		public TblRiskWhitePK getId() {
			return id;
		}
		public void setId(TblRiskWhitePK id) {
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
		public TblRiskWhite() {
		}
		
		
}

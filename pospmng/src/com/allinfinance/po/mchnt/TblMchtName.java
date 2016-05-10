package com.allinfinance.po.mchnt;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblMchtName implements Serializable{

	// primary key
		private TblMchtNamePK id;

		// fields
		private java.lang.String mchtNm;
		private java.lang.String onFlag;
		private java.lang.String msc1;
		private java.lang.String msc2;
		private java.lang.String msc3;
		
		public TblMchtName() {
//			super();
		}
		public TblMchtNamePK getId() {
			return id;
		}
		public void setId(TblMchtNamePK id) {
			this.id = id;
		}
		public java.lang.String getMchtNm() {
			return mchtNm;
		}
		public void setMchtNm(java.lang.String mchtNm) {
			this.mchtNm = mchtNm;
		}
		public java.lang.String getOnFlag() {
			return onFlag;
		}
		public void setOnFlag(java.lang.String onFlag) {
			this.onFlag = onFlag;
		}
		public java.lang.String getMsc1() {
			return msc1;
		}
		public void setMsc1(java.lang.String msc1) {
			this.msc1 = msc1;
		}
		public java.lang.String getMsc2() {
			return msc2;
		}
		public void setMsc2(java.lang.String msc2) {
			this.msc2 = msc2;
		}
		public java.lang.String getMsc3() {
			return msc3;
		}
		public void setMsc3(java.lang.String msc3) {
			this.msc3 = msc3;
		}
		
		
}
